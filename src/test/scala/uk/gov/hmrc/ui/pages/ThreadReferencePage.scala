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

import org.openqa.selenium.devtools.v135.page.Page
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver, WebElement}
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject

import java.time.Duration
import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalactic.Prettifier.default
import uk.gov.hmrc.ui.pages.AuthLoginPage.driver
import uk.gov.hmrc.ui.pages.ThreadReferencePage

import scala.jdk.CollectionConverters.*
import scala.xml.NodeSeq.Empty.text

object ThreadReferencePage extends BasePage {

  val headingLocator: By              = By.xpath("/html/body/header/div[1]/div/div[2]/a")
  val threadReferenceLocator: By      = By.cssSelector("#main-content > div > div > form > fieldset > legend > h1")
  val threadReferenceInputLocator: By = By.id("thread-reference")
  val continueButtonLocator: By       = By.xpath("//*[@id=\"main-content\"]/div/div/form/button")
  val errorTitleLocator: By           = By.xpath("//*[@id=\"main-content\"]/div/div/form/div[1]/div/h2")
  val threadReferenceErrorLocator: By = By.xpath("//*[@id=\"main-content\"]/div/div/form/div[1]/div/div/ul/li[1]/a")
  val threadRefSuccessful: By         = By.xpath("//*[@id=\"main-content\"]/div/div/div/div")
  val threadRefUnsuccessful: By       = By.cssSelector("#thread-reference-error")

  private val wait = new WebDriverWait(driver, Duration.ofSeconds(20))

  def getCaptionText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator)).getText.trim

  def getThreadReferenceText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceLocator)).getText.trim

  def getThreadReferenceInput: WebElement =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceInputLocator))

  def isThreadReferenceInputDisplayed: Boolean =
    driver.findElements(threadReferenceInputLocator).asScala.nonEmpty &&
      getThreadReferenceInput.isDisplayed

  def isThreadReferenceInputEnabled: Boolean =
    getThreadReferenceInput.isEnabled

  def enterThreadReference(value: String): Unit = {
    val input = getThreadReferenceInput
    input.clear()
    input.sendKeys(value)
  }

  def getContinueButton: WebElement =
    wait.until(ExpectedConditions.visibilityOfElementLocated(continueButtonLocator))

  def isContinueButtonDisplayed: Boolean =
    driver.findElements(continueButtonLocator).asScala.nonEmpty &&
      getContinueButton.isDisplayed

  def isContinueButtonEnabled: Boolean =
    getContinueButton.isEnabled

  def getContinueButtonText: String =
    getContinueButton.getText.trim

  def selectContinueButton(): Unit =
    getContinueButton.click()

  def isErrorTitleDisplayed: Boolean =
    driver.findElements(errorTitleLocator).asScala.nonEmpty &&
      wait.until(ExpectedConditions.visibilityOfElementLocated(errorTitleLocator)).isDisplayed

  def getErrorTitleText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(errorTitleLocator)).getText.trim

  def isThreadRefSuccessful: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadRefSuccessful)).getText.trim

  def isThreadRefUnsuccessful: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadRefUnsuccessful)).getText.trim

  def isInlineErrorDisplayed: Boolean =
    driver.findElements(threadReferenceErrorLocator).asScala.nonEmpty &&
      wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceErrorLocator)).isDisplayed

  def getInlineErrorText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceLocator)).getText.trim

  def isInlineErrorShownBelowInput: Boolean = {
    val errorLocation =
      wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceErrorLocator)).getLocation
    val inputLocation = getThreadReferenceInput.getLocation
    errorLocation.getY > inputLocation.getY
  }
}
