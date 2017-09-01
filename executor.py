import constants, sys, getopt
from quickumls import QuickUMLS
from twisted.web.test.test_cgi import READINPUT_CGI

class Executor:
    
    ### extractor params ###
    quickumls_fp = "../umls_2016_ncbi"
    overlapping_criteria = "score"
    threshold = 0.8
    minMatchedLength = 3
    similarity_name = "jaccard"
    window = 1
    
    ### input and output ###
    input_file = ""
    output_file = ""
    text = ""
    result = []
    
    
    def __init__(self, args):
        self.extractArgs(args)
        
    def run(self):
        self.readInput()
        
    def extract(self):
        print 'quickumls_fp: ' +self.quickumls_fp
        print 'overlapping_criteria: ' + self.overlapping_criteria
        print 'threshold: ' + str(self.threshold)
        print 'similarity_name: ' + self.similarity_name
        print 'minMatchedLength: ' +str(self.minMatchedLength)
        print 'window: ' + str(self.window)
    
        matcher = QuickUMLS(self.quickumls_fp, self.overlapping_criteria, self.threshold,
                        self.window, self.similarity_name, self.minMatchedLength,
                         constants.ACCEPTED_SEMTYPES, True)
    
        text = "The virus is not a mouse. Oh a Bacterium..."
        result = matcher.match(text, best_match=True, ignore_syntax=False)
        print(result)
        
    def extractArgs(self, args):
        try:
            opts, args = getopt.getopt(args,"hq:oc:t:m:s:w:-i:-o:",
                                    ["quickumls=", "overlapping=", "threshold=", "minMatched=", "similarity", "window", "input", "output"])
        except getopt.GetoptError:
            print("Error: Unknown argument")
            print self.getHelpString()
            sys.exit(2)
        
        for opt, arg in opts:
            if opt == '-h':
                print self.getHelpString()
                sys.exit()
            elif opt in ('-q', 'quickumls'):
                self.quickumls_fp = arg
            elif opt in ('-oc', 'overlapping'):
                self.overlapping_criteria= arg
            elif opt in ('-t', 'threshold'):
                self.threshold = arg
            elif opt in ('-m', 'minMatched'):
                self.minMatchedLength = arg
            elif opt in ('-s', 'similarity'):
                self.similarity_name= arg
            elif opt in ('-w', 'window'):
                self.window = arg
            elif opt in ('-i', 'input'):
                self.window = arg
            elif opt in ('-o', 'output'):
                self.window = arg
        
    
    def getHelpString(self):    
        return ("run.py \n-q <umls data> \n-o <overlapping criteria>"
                "\n-t <threshhold> \n-m <minimum matched length> \n"
                "-s <similarity name> -w <window> \n-i <input file path>"
                "-o <output file path>")
        
        
    def readInput(self):
        print("readFile")
        
    def readFile(self):
        print("readFile")

