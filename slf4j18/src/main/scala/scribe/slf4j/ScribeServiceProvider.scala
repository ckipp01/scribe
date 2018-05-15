package scribe.slf4j

import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.{ILoggerFactory, IMarkerFactory}
import org.slf4j.spi.{MDCAdapter, SLF4JServiceProvider}

class ScribeServiceProvider extends SLF4JServiceProvider {
  private lazy val markerFactory = new BasicMarkerFactory

  override def getLoggerFactory: ILoggerFactory = ScribeLoggerFactory

  override def getMarkerFactory: IMarkerFactory = markerFactory

  override def getMDCAdapter: MDCAdapter = ScribeMDCAdapter

  override def getRequesteApiVersion: String = "1.8.0-beta2"

  override def initialize(): Unit = {}
}