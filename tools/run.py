import sys
from executor import Executor

execute = Executor(sys.argv[1:])
execute.run()