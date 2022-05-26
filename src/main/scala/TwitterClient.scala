import cats.effect.IO
import cats.effect.unsafe.IORuntime
import io.circe.generic.decoding.DerivedDecoder.deriveDecoder
import org.http4s.Method.GET
import org.http4s._
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.{Client, JavaNetClientBuilder}
import org.http4s.headers.{Accept, Authorization}
import org.http4s.implicits.http4sLiteralsSyntax


final case class TwitterUser(id: String, name: String, username: String)
//
//
//
final case class DataInfo(data: Seq[TwitterUser])

object TwitterClient extends App {
  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  import java.util.concurrent._

  val blockingPool = Executors.newFixedThreadPool(5)
  val httpClient: Client[IO] = JavaNetClientBuilder[IO].create

  val authorization = Authorization(Credentials.Token(AuthScheme.Bearer, "AAAAAAAAAAAAAAAAAAAAANR4cwEAAAAAGikRbGRE9Fmzi2SumY6OCZ3SI3E%3DaAjQ8eUZIlKFeCi0rrlHjkAUQnJgXCPqYpNnNIuzoZI8PIhHsd"))
  val accept = Accept(MediaType.application.json)

  val request = Request[IO](
    GET,
    uri"https://api.twitter.com/2/users/by?usernames=techyfingers,jdegoes",
    HttpVersion(1,0),
    Headers(authorization, accept),
    Entity.Empty,
    org.typelevel.vault.Vault.empty
  )
//  Authorization(Credentials.Token(AuthScheme.Bearer, "AAAAAAAAAAAAAAAAAAAAANR4cwEAAAAAGikRbGRE9Fmzi2SumY6OCZ3SI3E%3DaAjQ8eUZIlKFeCi0rrlHjkAUQnJgXCPqYpNnNIuzoZI8PIhHsd")),
//  Accept(MediaType.application.json)
val result = httpClient.expect[DataInfo](request)

  println(result.unsafeRunSync())

}
