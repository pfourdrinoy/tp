import com.github.polomarcus.utils.ClimateService
import com.github.polomarcus.model.CO2Record
import org.scalatest.funsuite.AnyFunSuite

//@See https://www.scalatest.org/scaladoc/3.1.2/org/scalatest/funsuite/AnyFunSuite.html
class ClimateServiceTest extends AnyFunSuite {
  test("containsWordGlobalWarming - non climate related words should return false") {
    assert( ClimateService.isClimateRelated("pizza") == false)
  }

  test("isClimateRelated - climate related words should return true") {
    assert(ClimateService.isClimateRelated("climate change") == true)
    assert(ClimateService.isClimateRelated("IPCC"))
    assert(ClimateService.isClimateRelated("hello world")==false)
  }

  //@TODO
  test("parseRawData") {
    // our inputs
    val firstRecord = (2003, 1, 355.2)     //help: to acces 2003 of this tuple, you can do firstRecord._1
    val secondRecord = (2004, 1, 375.2)
    val list1 = List(firstRecord, secondRecord)

    // our output of our method "parseRawData"
    val co2RecordWithType = CO2Record(firstRecord._1, firstRecord._2, firstRecord._3)
    val co2RecordWithType2 = CO2Record(secondRecord._1, secondRecord._2, secondRecord._3)
    val output = List(Some(co2RecordWithType), Some(co2RecordWithType2))

    // we call our function here to test our input and output
    assert(ClimateService.parseRawData(list1) == output)
  }
  test("filterDecemberData") {
    // Sample optional CO2 records for testing
    val optionalRecords = List(
      Some(CO2Record(2003, 1, 355.2)),
      Some(CO2Record(2003, 12, 365.2)),
      Some(CO2Record(2004, 5, 375.2)),
      Some(CO2Record(2004, 12, 385.2))
    )

    // Expected filtered CO2 records (without December values)
    val expectedFilteredRecords = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2004, 5, 375.2)
    )

    // Call the filterDecemberData method
    val filteredRecords = ClimateService.filterDecemberData(optionalRecords)

    // Assertion
    assert(filteredRecords == expectedFilteredRecords)
  }
  test("getMinMax") {
    // Sample CO2 records for testing
    val records = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2004, 1, 375.2),
      CO2Record(2005, 1, 345.2),
      CO2Record(2006, 1, 385.2)
    )

    // Expected min and max ppm values
    val expectedMinPpm = 345.2
    val expectedMaxPpm = 385.2

    // Call the getMinMax method
    val (minPpm, maxPpm) = ClimateService.getMinMax(records)

    // Assertion
    assert(minPpm == expectedMinPpm)
    assert(maxPpm == expectedMaxPpm)
  }
  test("getMinMaxByYear") {
    // Sample CO2 records for testing
    val records = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2003, 2, 365.2),
      CO2Record(2004, 1, 375.2),
      CO2Record(2004, 2, 385.2)
    )

    // Expected min and max ppm values for year 2003
    val expectedMinPpm2003 = 355.2
    val expectedMaxPpm2003 = 365.2

    // Call the getMinMaxByYear method for year 2003
    val (minPpm2003, maxPpm2003) = ClimateService.getMinMaxByYear(records, 2003)

    // Assertion for year 2003
    assert(minPpm2003 == expectedMinPpm2003)
    assert(maxPpm2003 == expectedMaxPpm2003)

    // Expected min and max ppm values for year 2004
    val expectedMinPpm2004 = 375.2
    val expectedMaxPpm2004 = 385.2

    // Call the getMinMaxByYear method for year 2004
    val (minPpm2004, maxPpm2004) = ClimateService.getMinMaxByYear(records, 2004)

    // Assertion for year 2004
    assert(minPpm2004 == expectedMinPpm2004)
    assert(maxPpm2004 == expectedMaxPpm2004)
  }
  test("calculateDifference") {
    // Sample CO2 records for testing
    val records = List(
      CO2Record(2003, 1, 355.2),
      CO2Record(2003, 2, 365.2),
      CO2Record(2004, 1, 375.2),
      CO2Record(2004, 2, 385.2)
    )

    // Expected difference for the sample records
    val expectedDifference = 385.2 - 355.2

    // Call the calculateDifference method
    val actualDifference = ClimateService.calculateDifference(records)

    // Assertion
    assert(actualDifference == expectedDifference)
  }
  
  
}
