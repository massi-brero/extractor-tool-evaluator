import os, sys, glob, constants, getopt
from xml.etree.ElementTree import Element, SubElement, tostring
from xml.etree import ElementTree
from xml.dom import minidom
from quickumls import QuickUMLS


class Executor:
    
    FILE_EXTENSION = "*.*"
    
    ### extractor params ###
    quickumls_fp = "../umls_2016_ncbi"
    overlapping_criteria = "score"
    threshold = 0.8
    minMatchedLength = 3
    similarity_name = "jaccard"
    window = 1
    
    ### input and output ###
    input_path = ""
    output_file = ""
    text = ""
    result = []
    xml = None
    
    
    def __init__(self, args):
        self.extractArgs(args)
        
    def run(self):
        if (os.path.isfile(self.input_path)):
            self.readFile(self.input_path)
            self.extract(self.input_path)
        elif (os.path.isdir(self.input_path)):
            files = self.readFolder()   
            for file_name in files:
                self.readFile(file_name)
                self.extract(file_name)
        else:
            print("Error: Input path is not valid") 
            
        #print(self.result)
        self.writeOutput()
            
        
    def extract(self, file_name):
        #----------------------------- print 'quickumls_fp: ' +self.quickumls_fp
        #------------ print 'overlapping_criteria: ' + self.overlapping_criteria
        #----------------------------- print 'threshold: ' + str(self.threshold)
        #---------------------- print 'similarity_name: ' + self.similarity_name
        #---------------- print 'minMatchedLength: ' +str(self.minMatchedLength)
        #----------------------------------- print 'window: ' + str(self.window)
    
        matcher = QuickUMLS(self.quickumls_fp, self.overlapping_criteria, self.threshold,
                        self.window, self.similarity_name, self.minMatchedLength,
                         constants.ACCEPTED_SEMTYPES, True)
    
        extraction_result = matcher.match(self.text, best_match=True, ignore_syntax=False)
        self.buildXML(extraction_result, file_name)
        
    def extractArgs(self, args):
        try:
            opts, args = getopt.getopt(args,"hq:l:t:m:s:w:i:o:",
                                    ["quickumls=", "overlapping=", "threshold=", "minMatched=", "similarity=", "window=", "input=", "output="])
        except getopt.GetoptError:
            print("Error: Unknown argument")
            print self.getHelpString()
            sys.exit(2)

        for opt, arg in opts:
            if opt == '-h':
                print self.getHelpString()
                sys.exit()
            elif opt in ('-q', '--quickumls'):
                self.quickumls_fp = arg
            elif opt in ('l', '--overlapping'):
                self.overlapping_criteria= arg
            elif opt in ('-t', '--threshold'):
                self.threshold = arg
            elif opt in ('-m', '--minMatched'):
                self.minMatchedLength = arg
            elif opt in ('-s', '--similarity'):
                self.similarity_name= arg
            elif opt in ('-w', '--window'):
                self.window = arg
            elif opt in ('-i', '--input'):
                self.input_path = arg
            elif opt in ('-o', '--output'):
                self.output_file = arg
        
    
    def getHelpString(self):    
        return ("run.py \n-q <umls data> \n-l <overlapping criteria>"
                "\n-t <threshhold> \n-m <minimum matched length> \n"
                "-s <similarity name> -w <window> \n-i <input_path file path>"
                "\n-o <output_file file path>")
        
        
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

        
    def writeOutput(self):
        
        # print pretty
        rough_string = ElementTree.tostring(self.xml, 'utf-8')
        reparsed = minidom.parseString(rough_string)
        output_string =  reparsed.toprettyxml(indent="  ")
        
        try:
            with open(self.output_file, 'w') as f:
                f.write(output_string)
        except (IOError, OSError) as e:
            print ("Error: while writing result file.\n" + str(e))
            
    ### xml builder ###
    def buildXML(self, extraction_result, file_name):
        self.result.extend(extraction_result)
        
        if (self.xml is None):
            self.xml = Element("output")
        
        document = SubElement(self.xml, "document", {"file": file_name})
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
                    child.text = str(value)


