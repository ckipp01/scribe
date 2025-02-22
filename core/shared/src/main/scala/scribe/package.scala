import sourcecode.{FileName, Line, Name, Pkg}

import scala.language.experimental.macros
import scala.language.implicitConversions

package object scribe extends LoggerSupport {
  lazy val lineSeparator: String = System.getProperty("line.separator")

  protected[scribe] var disposables = Set.empty[() => Unit]

  @inline
  override final def log[M](record: LogRecord[M]): Unit = Logger(record.className).log(record)

  override def log[M: Loggable](level: Level, message: => M, throwable: Option[Throwable])
                               (implicit pkg: Pkg, fileName: FileName, name: Name, line: Line): Unit = {
    if (includes(level)) super.log(level, message, throwable)
  }

  def includes(level: Level)(implicit pkg: sourcecode.Pkg,
                             fileName: sourcecode.FileName,
                             name: sourcecode.Name,
                             line: sourcecode.Line): Boolean = {
    val (_, className) = LoggerSupport.className(pkg, fileName)
    Logger(className).includes(level)
  }

  def dispose(): Unit = disposables.foreach(d => d())

  implicit def level2Double(level: Level): Double = level.value

  implicit class AnyLogging(value: Any) {
    def logger: Logger = Logger(value.getClass.getName)
  }
}