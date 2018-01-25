import sys
from mm_corrector import MMCorrector

execute = MMCorrector(sys.argv[1])
execute.run()