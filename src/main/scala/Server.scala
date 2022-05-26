import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
//implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

class Server {
  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }
  // helloWorldService: HttpRoutes[IO] = Kleisli(
  //   run = org.http4s.HttpRoutes$$$Lambda$21885/683693719@7f80610f
  // )

  case class Tweet(id: Int, message: String)

  implicit def tweetEncoder: EntityEncoder[IO, Tweet] = ???
  implicit def tweetsEncoder: EntityEncoder[IO, Seq[Tweet]] = ???

  def getTweet(tweetId: Int): IO[Tweet] = ???
  def getPopularTweets(): IO[Seq[Tweet]] = ???

  val tweetService = HttpRoutes.of[IO] {
    case GET -> Root / "tweets" / "popular" =>
      getPopularTweets().flatMap(Ok(_))
    case GET -> Root / "tweets" / IntVar(tweetId) =>
      getTweet(tweetId).flatMap(Ok(_))
  }
  // tweetService: HttpRoutes[IO] = Kleisli(
  //   run = org.http4s.HttpRoutes$$$Lambda$21885/683693719@7e485e9e
  // )
}