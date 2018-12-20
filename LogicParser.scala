import scala.util.parsing.combinator._



  abstract class Tree
case class Ch(n: Char) extends Tree
case class Boo(b: Boolean) extends Tree
case class E(m: Tree) extends Tree
case class Not(r: Tree) extends Tree
  case class Or(l: Tree, r: Tree) extends Tree
  case class And(l: Tree, r: Tree) extends Tree



  object Builder extends Definitions {
    type Environment = String => String

    def evaluate(t: Tree, evo: Environment): String = t match {
      case E(m) if(evaluate(m, evo) == "true") => "true"
      case E(m) if(evaluate(m, evo) == "false") => "false"
      case E(m) => "(" + evaluate(m, evo) + ")"
      case Ch(n) => "" + n
      case Boo(b) => "" + b
      case Not(r) if(evaluate(r, evo) == "true") => "false"
      case Not(r) if(evaluate(r, evo) == "false") => "true"
      case Not(r) => "!" + (evaluate(r, evo))
      case Or(l, r) if(evaluate(l, evo) == "true") => "true"
      case Or(l, r) if(evaluate(l, evo) == "false")=> evaluate(r, evo)
      case Or(l, r) if(evaluate(r, evo) == "true") => "true"
      case Or(l, r) if(evaluate(r, evo) == "false") => evaluate(l, evo)
      case Or(l, r) => evaluate(l, evo) + "||" + evaluate(r, evo)
      case And(l, r) if(evaluate(l,evo) == "true") => evaluate(r, evo)
      case And(l, r) if(evaluate(l, evo) == "false") => "false"
      case And(l, r) if(evaluate(r, evo) == "true") => evaluate(l, evo)
      case And(l, r) if(evaluate(r, evo) == "false") => "false"
      case And(l, r) => evaluate(l, evo) + "&&" + evaluate(r, evo)

    }

    def main(args: Array[String]) ={
      print("Write your Expression or 'q' to quit")
      var in : String = scala.io.StdIn.readLine()
      while(in != "q") {
        val out:Tree = parseAll(e, in).get
        val evo: Environment = {
          case "a" => "a"
        }
        println("result: " + evaluate(out, evo))
        print("Write your Expression or 'q' to quit")
        in = scala.io.StdIn.readLine()
      }
    }
  }

  class Definitions extends JavaTokenParsers {

    def e: Parser[Tree] = t ~ or ~ e ^^ {case l ~ o ~ r => Or(l, r)} | t
    def t: Parser[Tree] = f ~ and ~ t ^^ {case l ~ a ~ r => And(l, r)} | f
    def f: Parser[Tree] = not ~ a ^^ {case n ~ r => Not(r)} | a
    def a: Parser[Tree] = para1 ~ e ~ para2 ^^ {case l ~ m ~ r => E(m)} | c
    def c: Parser[Tree] = bol | chr
    def chr: Parser[Ch] = "[A-Za-z]".r ^^ {str => Ch(str.charAt(0))}
    def bol: Parser[Boo] = "true".r ^^ {str => Boo(true)} | "false".r ^^ {str => Boo(false)}
    def not[Tree] = "!"
    def and[Tree] = "&&"
    def or[Tree] = "||"
    def para1[Tree] = "("
    def para2[Tree] = ")"
  }



