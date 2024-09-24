package st.catseffects

import cats.effect.{ExitCode, IO, IOApp, Resource}

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}
import scala.concurrent.duration.*
import scala.language.postfixOps

object C_Res extends IOApp {


  def inputStream(f: File): Resource[IO, FileInputStream] = {
    Resource.make {
      IO.blocking(new FileInputStream(f))
    } { inStream => {
      IO.blocking(inStream.close()).handleErrorWith(err => IO.println(err))
    }}
  }


  def outputStream(f: File): Resource[IO, FileOutputStream] = {
    Resource.make {
      IO.blocking(new FileOutputStream(f))
    } {oStream =>
      IO.blocking(oStream.close()).handleErrorWith(err => IO.println(s"err: $err"))
    }
  }

  def inputOutStream(in: File, out: File): Resource[IO, (InputStream, OutputStream)] =
    for {
      inStream <- inputStream(in)
      outStream <- outputStream(out)
    } yield (inStream, outStream)


  def transmit(src: InputStream, dest: OutputStream, buffer: Array[Byte], acc: Long): IO[Long] =
    for {
      amount <- IO.blocking(src.read(buffer, 0, buffer.length))
      count <- if (amount > -1) IO.blocking(dest.write(buffer, 0, amount)) >> transmit(src, dest, buffer, acc + amount)
                else IO.pure(acc)
    } yield count

  def transfer(in: InputStream, out: OutputStream): IO[Long] =
    transmit(in, out, new Array[Byte](1024 * 10), 0)

  def copy(origin: File, dest: File): IO[Long] =
    inputOutStream(origin, dest).use {case (in, out) => transfer(in, out)}

  override def run(args: List[String]): IO[ExitCode] = {
//    lazy val loopIO: IO[Unit] = IO.println("repeat ...") >> IO.sleep(0.5.seconds) >> loopIO
//    IO.println("Hello")
//      >> IO.println("world")
//      >> IO(println("Finished")).as(ExitCode.Success)
    for {
      _ <- if (args.length < 2) IO.raiseError(new IllegalArgumentException("need origin and destination files")) else IO.unit
      origin = new File(args.head)
      destination = new File(args(1))
      count <- copy(origin, destination)
      _ <- IO.println(s"$count bytes copied from $origin to $destination")
    } yield ExitCode.Success
  }
}