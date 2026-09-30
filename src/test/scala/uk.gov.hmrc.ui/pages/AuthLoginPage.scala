/*
 * Copyright 2026 HM Revenue & Customs
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

import org.openqa.selenium.{By, JavascriptExecutor, WebElement}
import org.openqa.selenium.support.ui.{ExpectedConditions, Select, WebDriverWait}
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.driver.BrowserDriver
import scala.jdk.CollectionConverters.*

object AuthLoginPage extends BrowserDriver with BasePage {

  val url:                String = s"${TestConfiguration.url("auth-login-stub")}/gg-sign-in"
  val frontEndUrl:        String = TestConfiguration.url("sdec-external-frontend")
  val threadReferenceUrl: String = "http://localhost:4502/sdec-alpha"

  object Fields {
    val credId:          By = By.id("authorityId")
    val redirectUrl:     By = By.id("redirectionUrl")
    val userName:        By = By.id("usersName")
    val emailAddress:    By = By.id("email")
    val confidenceLevel: By = By.id("confidenceLevel")
    val ninoInput:       By = By.id("nino")
  }

  private val redirectUrls: Map[String, String] = Map(
    "sdec-external-frontend" -> frontEndUrl,
    "sdec-thread-reference"  -> threadReferenceUrl
  )

  private def resolveRedirect(page: String): String =
    redirectUrls.getOrElse(
      page,
      throw new IllegalArgumentException(s"Unknown redirect page: $page")
    )

  def login(): Unit = {
    navigateTo(url)
    sendKeys(Fields.redirectUrl, resolveRedirect("sdec-external-frontend"))

  }

  def getConfidenceLevel: WebElement =
    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Fields.confidenceLevel))

  def getNino: WebElement =
    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Fields.ninoInput))

  def getUserName: WebElement =
    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Fields.userName))

  def getEmail: WebElement =
    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(Fields.emailAddress))

  def selectConfidenceLevel(): Unit = {
    val select = new Select(getConfidenceLevel)
    select.getOptions.asScala.toList
    select.selectByVisibleText("200")
  }

  def enterNino(value: String): Unit = {
    val input = getNino
    input.clear()
    input.sendKeys(value)
  }

  def enterUserName(value: String): Unit = {
    val input = getUserName
    input.clear()
    input.sendKeys(value)
  }

  def enterEmailAddress(value: String): Unit = {
    val input = getEmail
    input.clear()
    input.sendKeys(value)
  }

  def authIdent(
  ): Unit = {
    val jsExecutor = driver.asInstanceOf[JavascriptExecutor]
    driver.manage().deleteAllCookies()
    jsExecutor.executeScript("window.localStorage.clear();")
    jsExecutor.executeScript("window.sessionStorage.clear();")
    driver.navigate().refresh()
    navigateTo(threadReferenceUrl)
  }

}
