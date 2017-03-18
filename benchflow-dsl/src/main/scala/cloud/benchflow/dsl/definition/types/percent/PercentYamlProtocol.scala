package cloud.benchflow.dsl.definition.types.percent

import net.jcazevedo.moultingyaml.{DefaultYamlProtocol, YamlFormat, YamlString, YamlValue, _}

import scala.util.{Failure, Success, Try}

/**
  * @author Jesper Findahl (jesper.findahl@usi.ch) 
  *         created on 14.03.17.
  */
object PercentYamlProtocol extends DefaultYamlProtocol {

  implicit object PercentFormat extends YamlFormat[Try[Percent]] {

    override def read(yaml: YamlValue): Try[Percent] = {

      val stringValue: Try[String] = Try(yaml.asInstanceOf[YamlString].value)

      stringValue match {
        case Success(string) => Try(Percent(string.replace("%", "").toDouble / 100))
        case Failure(exception) => Failure(exception)
      }

    }

    override def write(obj: Try[Percent]): YamlValue = obj.get.toString.toYaml

  }

}
