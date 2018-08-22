package io.ctek.interview.dataeng

import cats.data.{Kleisli, ReaderT}
import cats.instances.try_._
import scala.util.Try

case class DataEnvironment(data: Set[Int])

class MathService {
  def gcd: ReaderT[Try, DataEnvironment, Either[String, Int]] = for {
    data <- retrieveData
  } yield data.map(_.reduceLeft(gcd))

  def gcd(a: Int, b: Int): Int = b match {
    case 0 => a
    case _ => gcd(b, a % b)
  }

  def retrieveData: ReaderT[Try, DataEnvironment, Either[String, Set[Int]]] = Kleisli {
    (env) =>
      Try {
        if (env.data.size == 1 && env.data.contains(0)) {
          Left("gcd of 0 is undefined")
        } else {
          Right(env.data)
        }
      }
  }
}
