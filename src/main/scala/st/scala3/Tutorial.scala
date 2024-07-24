package st.scala3

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.kernel.Resource

import java.io.{File, FileInputStream, FileOutputStream, InputStream, OutputStream}


def inputStream(f: File): Resource[IO, FileInputStream] = {
  Resource.make {
    IO.blocking(new FileInputStream(f))
  } {inStream => {
    IO.blocking(inStream.close()).handleErrorWith(_ => IO.unit)
  }}
}


def outputStream(f: File): Resource[IO, FileOutputStream] = {
  Resource.make {
    IO.blocking(new FileOutputStream(f))
  } {outStream => {
    IO.blocking(outStream.close()).handleErrorWith(_ => IO.unit)
  }}
}


def inputOutputStream(in: File, out: File): Resource[IO, (InputStream, OutputStream)] = {
  for {
    inStream <- inputStream(in)
    outStream <- outputStream(out)
  } yield (inStream, outStream)
}


def transmit(origin: InputStream, dest: OutputStream, buffer: Array[Byte], acc: Long): IO[Long] = {
  for {
    amount <- IO.blocking(origin.read(buffer, 0, buffer.length))
    count <- if (amount > -1) IO.blocking(dest.write(buffer, 0, amount)) >> transmit(origin, dest, buffer, acc + amount)
    else IO.pure(acc)
  } yield count
}

def transfer(origin: InputStream, dest: OutputStream): IO[Long] =
  transmit(origin, dest, new Array[Byte](1024 * 10), 0L)

def copyFile(origin: File, dest: File): IO[Long] = {
  inputOutputStream(origin, dest).use {
    case (in, out) => transfer(in, out)
  }
}

object Tutorial extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- if (args.length < 2) IO.raiseError(new IllegalArgumentException("need origin and dest")) else IO.unit
      origin = new File(args.head)
      dest = new File(args(1))
      count <- copyFile(origin ,dest)
      _ <- IO.println(s"$count bytes copied from $origin -> $dest")
    } yield ExitCode.Success
  }
}
