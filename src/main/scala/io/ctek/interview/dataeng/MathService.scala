package io.ctek.interview.dataeng

import cats.effect.Sync
import cats.implicits._

case class DataEnvironment(data: Set[Int])

class MathService[F[_]](implicit F: Sync[F]) {
  def gcd(data: DataEnvironment): F[Either[String, Int]] =
    retrieveData(data)
      .map(_.map(_.reduceLeft(gcd)))

  def gcd(a: Int, b: Int): Int =
    b match {
      case 0 => a
      case _ => gcd(b, a % b)
    }

  def retrieveData(
    env: DataEnvironment
  ): F[Either[String, Set[Int]]] =
    if (env.data.size == 1 && env.data.contains(0)) {
      F.delay(Left("gcd of 0 is undefined"))
    } else {
      F.delay(Right(env.data))
    }
}