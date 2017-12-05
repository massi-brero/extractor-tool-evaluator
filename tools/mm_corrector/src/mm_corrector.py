import os, glob
from fileinput import filename

# corrects faulty Metamap XML files that are missing the closing tag <MMOs>
# test url /home/massi/projects/result_files/extractor_results/mm_test
class MMCorrector:
    
    input_path = ""
    CLOSING_TAG = "</MMOs>"
    WRONG_LAST_LINE = "</MMO>"
    FILE_EXTENSION = "*.*"
    NEWLINE = "\n"
    
    def __init__(self, inputP):
        self.input_path = inputP
    
    def run(self):
        
        try:
         
            if (os.path.isfile(self.input_path)):
                self.correctXML(self.input_path)    
                
            elif (os.path.isdir(self.input_path)):
                
                files = self.readFolder()   
                for file_item in files:
                    self.correctXML(file_item)
                    
            else:
                raise Exception("Error: Input path is not valid") 
            
        except Exception as e:
            print(e)
            
    
    def correctXML(self, file_name):
        try:
            i = -1
            with open(file_name, "ab+") as f:
                last_line = ""
                while(not last_line):
                    last_line = f.readlines()[i]
                    i -= 1
                    
                print(file_name)    
                if (self.WRONG_LAST_LINE in last_line):
                    print("*** Repairing: " + file_name)
                    f.write(self.NEWLINE + self.CLOSING_TAG)
        except (IOError, OSError) as e:
            print ("Error: while reading your input path.\n" + str(e))
            
    def readFolder(self):
        path = self.input_path
        
        if (not self.input_path.endswith("/")):
            path = path + "/"
            
        return glob.glob(path + self.FILE_EXTENSION)

        