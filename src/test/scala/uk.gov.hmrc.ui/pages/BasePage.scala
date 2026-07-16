/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Wait}
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver

import java.lang
import java.time.Duration
import scala.jdk.CollectionConverters.*

trait BasePage extends Matchers with PageObject {

  val submitButtonId: By = By.id("submit-top")

  private def fluentWait(timeoutSeconds: Long): Wait[WebDriver] =
    new FluentWait[WebDriver](Driver.instance)
      .withTimeout(Duration.ofSeconds(timeoutSeconds))
      .pollingEvery(Duration.ofMillis(200))
      .ignoring(classOf[org.openqa.selenium.StaleElementReferenceException])
      .ignoring(classOf[org.openqa.selenium.NoSuchElementException])

  def continue(locator: By = submitButtonId): Unit = {
    assertLocatorPresent(locator)
    click(locator)
  }

  def navigateTo(url: String, timeoutSeconds: Long = 5): Unit = {
    val driver = Driver.instance
    val js     = driver.asInstanceOf[JavascriptExecutor]

    js.executeScript(s"window.location.href='$url'")
  }

  def assertLocatorPresent(locator: By): Unit = {
    val elements = Driver.instance.findElements(locator).asScala
    require(
      elements.nonEmpty,
      s"Expected element with locator [$locator] to be present, but none was found"
    )
  }
}
