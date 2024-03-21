package scala.u04.task2

/*
type:
  Sequence[A]

constructors:
  cons: A x Sequence[A] => Sequence[A]
  nil: Sequence[A]

operations :
  filter[A]: Sequence[A] x (A => Boolean) => Sequence[A]
  map[A,B]: Sequence[A] x (A => B) => Sequence[B]
  flatMap[A,B]: Sequence[A] x (A => Sequence[B]) => Sequence[B]

axioms :
  filter(nil, f) = nil
  filter(cons(h,t),f)) = if f(h) => Cons(h, filter(t, f)) else filter(t, f)

  concat(s1, nil) = s1
  concat(nil, s2) = s2
  concat(cons(h, t), s2) = cons(h, concat(t, s2))

  map(nil, f) = nil
  map (cons(h, t), f) = cons(f(h), map(t, f))

  flatMap(nil, f) = nil
  flatMap(cons(h, t), f) = concat(f(h), flatMap(t, f))

  foldLeft(nil, z, _) = z
  foldLeft(cons(h, t), z, op) = fold(t, op(z, h), op)

  reduce(nil, _) = nil
  reduce(cons(h, t), op) = foldLeft(t, op(h), op)
 */

object Sequences:

  trait SequenceADT:
    type Sequence[A]
    def cons[A](h: A, t: Sequence[A]): Sequence[A]
    def nil[A](): Sequence[A]
    def map[A, B](sequence: Sequence[A], mapper: A=>B): Sequence[B]
    def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A]
    def filter[A](sequence: Sequence[A], predicate: A => Boolean): Sequence[A]
    def flatMap[A, B](sequence: Sequence[A], mapper: A => Sequence[B]): Sequence[B]
    def foldLeft[A, B](sequence: Sequence[A], z: B, op: (B, A) => B): B
    def reduce[A](sequence: Sequence[A], op: (A, A) => A): A
  
  object BasicSequenceADT extends SequenceADT:
    private enum SequenceImpl[A]:
      case Cons(a: A, t: Sequence[A])
      case Nil()
      
    import SequenceImpl.*

    opaque type Sequence[A] = SequenceImpl[A]

    override def cons[A](h: A, t: Sequence[A]): Sequence[A] = Cons(h, t)

    override def nil[A](): Sequence[A] = Nil()

    override def map[A, B](sequence: Sequence[A], mapper: A => B): Sequence[B] = sequence match
      case Cons(h, t) => Cons(mapper(h), map(t, mapper))
      case _ => Nil()

    override def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A] = (s1, s2) match
      case (s1, Nil()) => s1
      case (Nil(), s2) => s2
      case (Cons(h, t), s2) => Cons(h, concat(t, s2))

    override def filter[A](sequence: Sequence[A], predicate: A => Boolean): Sequence[A] = sequence match
      case Cons(h, t) if predicate(h) => Cons(h, filter(t, predicate))
      case _ => Nil()
      
    override def flatMap[A, B](sequence: Sequence[A], mapper: A => Sequence[B]): Sequence[B] = sequence match
      case Cons(h, t) => concat(mapper(h), flatMap(t, mapper))
      case _ => Nil()

    override def foldLeft[A, B](sequence: Sequence[A], z: B, op: (B, A) => B): B = sequence match
      case Cons(h, t) => foldLeft(t, op(z, h), op)
      case Nil() => z

    override def reduce[A](sequence: Sequence[A], op: (A, A) => A): A = sequence match
      case Cons(h, t) => foldLeft(t, h, op)
      case Nil() => throw UnsupportedOperationException()


  object ScalaListSequenceADT extends SequenceADT:
    opaque type Sequence[A] = List[A]

    override def cons[A](h: A, t: Sequence[A]): Sequence[A] = h :: t

    override def nil[A](): Sequence[A] = List()

    override def map[A, B](sequence: Sequence[A], mapper: A => B): Sequence[B] = sequence.map(mapper)

    override def concat[A](s1: Sequence[A], s2: Sequence[A]): Sequence[A] = s1 ++ s2

    override def filter[A](sequence: Sequence[A], predicate: A => Boolean): Sequence[A] = sequence.filter(predicate)

    override def flatMap[A, B](sequence: Sequence[A], mapper: A => Sequence[B]): Sequence[B] = sequence.flatMap(mapper)

    override def foldLeft[A, B](sequence: Sequence[A], z: B, op: (B, A) => B): B = sequence.foldLeft(z)(op)

    override def reduce[A](sequence: Sequence[A], op: (A, A) => A): A = sequence.reduce(op)
