import os, sys, glob, constants, getopt
from xml.etree.ElementTree import Element, SubElement, tostring
from xml.etree import ElementTree
from xml.dom import minidom
from quickumls import QuickUMLS
from fileinput import filename
from twisted.trial._synctest import Todo


class Executor:
    
    FILE_EXTENSION = "*.*"
    DIR_PATH = os.path.dirname(os.path.realpath(__file__))
    CUI_TAG = "cui"
    
    ### extractor params ###
    quickumls_fp = DIR_PATH + "/../umls_2016_ncbi"
    overlapping_criteria = "score"
    threshold = 0.8
    minMatchedLength = 3
    similarity_name = "jaccard"
    window = 1
    
    ### input and output ###
    input_path = ""
    output_path = ""
    text = ""
    result = []
    xml = None
    
    
    def __init__(self, args):
        self.extractArgs(args)
        
    def run(self):
        
        output_file = self.output_path;
        
        try:
            if os.path.isdir(self.output_path):
                raise Exception("Please enter a path (not a target file) for the output files")
         
            if (os.path.isfile(self.input_path)):
                
                self.readFile(self.input_path)
                self.extract(self.input_path)
                # @Todo continue here
                self.writeOutput(self.buildTargetFilePath())
                
            elif (os.path.isdir(self.input_path)):
                
                files = self.readFolder()   
                
                for file_name in files:
                    self.readFile(file_name)
                    self.extract(file_name)
                    
                    if self.output_path.isdir():
                        output_file = self.output_path + "/" + filename
                    else:
    
                        self.writeOutput(output_file)
            else:
                raise Exception("Error: Input path is not valid") 
            
        except Exception as e:
            print(e)
            
        #print(self.result)
            
        
    def extract(self, file_name):
        print 'quickumls_fp: ' +self.quickumls_fp
        print 'overlapping_criteria: ' + self.overlapping_criteria
        print 'threshold: ' + str(self.threshold)
        print 'similarity_name: ' + self.similarity_name
        print 'minMatchedLength: ' +str(self.minMatchedLength)
        print 'window: ' + str(self.window)
    
        matcher = QuickUMLS(self.quickumls_fp, self.overlapping_criteria, self.threshold,
                        self.window, self.similarity_name, self.minMatchedLength,
                         constants.ACCEPTED_SEMTYPES, True)
    
        extraction_result = matcher.match(self.text, best_match=True, ignore_syntax=False)
        self.buildXML(extraction_result, file_name)
        
    def extractArgs(self, args):
        
        hasInputParam = False
        hasOutputParam = False
        
        try:
            opts, args = getopt.getopt(args,"hq:l:t:m:s:w:i:o:",
                                    ["quickumls=", "overlapping=", "threshold=", "minMatched=", "similarity=", "window=", "input=", "output="])
        
        
            for opt, arg in opts:
                if opt == '-h':
                    print self.getHelpString()
                    sys.exit()
                elif opt in ('-q', '--quickumls'):
                    self.quickumls_fp = arg
                elif opt in ('l', '--overlapping'):
                    self.overlapping_criteria= arg
                elif opt in ('-t', '--threshold'):
                    self.threshold = float(arg)
                elif opt in ('-m', '--minMatched'):
                    self.minMatchedLength = int(arg)
                elif opt in ('-s', '--similarity'):
                    self.similarity_name= arg
                elif opt in ('-w', '--window'):
                    self.window = int(arg)
                elif opt in ('-i', '--input'):
                    self.input_path = arg
                    hasInputParam = True
                elif opt in ('-o', '--output'):
                    self.output_path = arg
                    hasOutputParam = True
                    
            if not hasInputParam:
                raise Exception("Please specify an input path or an input file") 
            
            if not hasOutputParam:
                raise Exception("Please specify an output file") 
                            
        except getopt.GetoptError:
            print("Error: Unknown argument")
            print self.getHelpString()
            sys.exit(2)
            
        except Exception as e:
            print(e)
            print self.getHelpString()
            sys.exit(2)
            
        
    
    def getHelpString(self):    
        return ("run.py \n-q <umls data> \n-l <overlapping criteria>"
                "\n-t <threshhold> \n-m <minimum matched length> \n"
                "-s <similarity name> -w <window> \n-i <input_path file path>"
                "\n-o <output_path file path>\n\ninput path and output file are mandatory")
        
        
    def readFolder(self):
        path = self.input_path
        
        if (not self.input_path.endswith("/")):
            path = path + "/"
            
        return glob.glob(path + self.FILE_EXTENSION)
        
        
    def readFile(self, file_name):
        try:
            with open(file_name) as f:
                self.text = f.readlines()
        except (IOError, OSError) as e:
            print ("Error: while reading your input path.\n" + str(e))

        
    def writeOutput(self, filePath):
        
        # print pretty
        rough_string = ElementTree.tostring(self.xml, 'utf-8')
        reparsed = minidom.parseString(rough_string)
        output_string =  reparsed.toprettyxml(indent="  ")
        
        try:
            with open(filePath, 'w') as f:
                f.write(output_string)
        except (IOError, OSError) as e:
            print ("Error: while writing result file.\n" + str(e))
            
    ### xml builder ###
    def buildXML(self, extraction_result, file_name):
        self.result.extend(extraction_result)
        
        if (self.xml is None):
            self.xml = Element("output")
        
        document = SubElement(self.xml, "document", {"file": self.extractFilename(file_name)})
        self.addConceptsToXML(document, extraction_result)
        

    def addConceptsToXML(self, element, concepts):
        if (type(element) is Element):
            for item in concepts:
                if (any(isinstance(x, dict)) for x in item):
                    self.addConceptNode(element, item)
                else:
                    self.addConceptsToXML(element, item)
            

            
    def addConceptNode(self, element, concepts):
        matched_concepts = SubElement(element, "concepts_matched")
        
        if(type(element) is Element):
            for concept in concepts:
                conceptNode = SubElement(matched_concepts, "concept")
                for key, value in concept.items():
                    child = SubElement(conceptNode, key)
                    if key == self.CUI_TAG:
                        child = SubElement(conceptNode, "id")
                        child.set(self.CUI_TAG, value)
                    else:
                        child.text = str(value)
                        
    def buildTargetFilePath(self, path):
        file_name = self.extractFilename(path)
        output_path = self.output_path + "/" + file_name + ".out"
        
        
                        
    def extractFilename(self, path):
        try:    
            pathComponents = path.split("/")
            fileName = pathComponents[-1]
        except IndexError:
            fileName = "no-file-found"
            
        return fileName


