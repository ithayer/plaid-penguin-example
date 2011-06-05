namespace java plaid_penguin_example

enum OpType {
  SLEEP = 1
}

struct Operation {
  1: OpType op,
  2: string message
}

enum Result {
  OK = 1,
  BAD = 2
}

service OpService {
  Result do_op(1:Operation op)
}