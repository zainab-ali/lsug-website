package lsug

import scala.concurrent.duration._
import cats.effect.concurrent.Ref
import cats.implicits._
import cats.effect._

final class CacheSpec extends IOSuite {

  // We pretend that the number of attendees increases over time
  def unsafeAttendeeCount: Long = System.currentTimeMillis() / 1000L

  // Time in seconds after which we fetch a new value 
  val evictionTime: Duration = 1.second

  // TODO: You'll need to change this datatype
  final case class Cache(numberOfAttendees: Long)

  def createCache: IO[Cache] = ???

  def cachedAttendeeCount(ref: Cache): IO[Long] = ???

  test("returns the same cached value when called immediately") {
    for {
      cache <- createCache
      count1 <- cachedAttendeeCount(cache)
      count2 <- cachedAttendeeCount(cache)
    } yield assertEquals(count1, count2)
  }

  test("returns a different value after waiting") {
    import scala.concurrent.ExecutionContext.Implicits.global
    implicit val timer: Timer[IO] = IO.timer(global)
    for {
      cache <- createCache
      count1 <- cachedAttendeeCount(cache)
      _ <- IO.sleep(5.seconds)
      count2 <- cachedAttendeeCount(cache)
    } yield assert(count1 < count2)
  }

}
