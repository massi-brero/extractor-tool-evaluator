import os, sys, glob, constants, getopt
from quickumls import QuickUMLS
from twisted.web.test.test_cgi import READINPUT_CGI
from fileinput import filename

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
    
    
    def __init__(self, args):
        self.extractArgs(args)
        
    def run(self):
        if (os.path.isfile(self.input_path)):
            self.readFile(self.input_path)
            self.extract()
        elif (os.path.isdir(self.input_path)):
            files = self.readFolder()   
            for file_name in files:
                self.readFile(file_name)
                self.extract()
        else:
            print("Error: Input path is not valid") 
            
        print(self.result)
        self.writeOutput()
            
        
    def extract(self):
        #----------------------------- print 'quickumls_fp: ' +self.quickumls_fp
        #------------ print 'overlapping_criteria: ' + self.overlapping_criteria
        #----------------------------- print 'threshold: ' + str(self.threshold)
        #---------------------- print 'similarity_name: ' + self.similarity_name
        #---------------- print 'minMatchedLength: ' +str(self.minMatchedLength)
        #----------------------------------- print 'window: ' + str(self.window)
    
        matcher = QuickUMLS(self.quickumls_fp, self.overlapping_criteria, self.threshold,
                        self.window, self.similarity_name, self.minMatchedLength,
                         constants.ACCEPTED_SEMTYPES, True)
    
        self.result.append(matcher.match(self.text, best_match=True, ignore_syntax=False))
        
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
        try:
            with open(self.output_file, 'w') as f:
                items = [str(x) for x in self.result[0][0]]
                f.write(','.join(items))
        except (IOError, OSError) as e:
            print ("Error: while writing result file.\n" + str(e))


