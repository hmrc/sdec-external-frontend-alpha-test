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

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebElement}
import uk.gov.hmrc.ui.pages.AuthLoginPage.driver

import java.time.Duration
import scala.jdk.CollectionConverters.*

object ThreadReferencePage extends BasePage {

  val headingLocator:              By = By.xpath("/html/body/header/div[1]/div/div[2]/a")
  val threadReferenceLocator:      By = By.cssSelector("#main-content h1.govuk-fieldset__heading")
  val threadReferenceInputLocator: By = By.id("thread-reference")
  val continueButtonLocator:       By = By.cssSelector("#main-content button.govuk-button")
  val errorTitleLocator: By = By.cssSelector("#main-content form div.govuk-error-summary h2.govuk-error-summary__title")
  val threadReferenceErrorLocator: By = By.xpath("//*[@id=\"main-content\"]/div/div/form/div[1]/div/div/ul/li[1]/a")
  val threadRefSuccessful:         By = By.cssSelector("#main-content span.govuk-caption-l")
  val threadRefUnsuccessful:       By = By.id("thread-reference-error")
  val redirectPage:                By = By.cssSelector("#main-content h1.govuk-heading-l")
  val threadRefButton:             By = By.cssSelector("#main-content > div > div > a:nth-child(2)")
  val threadRefPageName:           By = By.cssSelector("#main-content h1.govuk-heading-l")
  val acceptCookiesButton:         By = By.name("cookies")
  val externalUserNameLocator:     By = By.cssSelector("#main-content h1")
  val messageLocator:              By = By.cssSelector("#main-content ol.hmrc-timeline  li.hmrc-timeline__event p")
  val createdDateLocator:          By = By.cssSelector("#main-content ol.hmrc-timeline li.hmrc-timeline__event time")

  private val wait = new WebDriverWait(driver, Duration.ofSeconds(20))

  def getCaptionText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator)).getText.trim

  def getExternalUserNameText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(externalUserNameLocator)).getText.trim

  def getMessageText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator)).getText.trim

  def getCreatedDateText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(createdDateLocator)).getText.trim

  def getRedirectPageText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(redirectPage)).getText.trim

  def getThreadReferenceText: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadReferenceLocator)).getText.trim

  def getThreadRefPageName: String =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadRefPageName)).getText.trim

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

  def getAcceptCookiesButton: WebElement =
    wait.until(ExpectedConditions.visibilityOfElementLocated(acceptCookiesButton))

  def getThreadRefButton: WebElement =
    wait.until(ExpectedConditions.visibilityOfElementLocated(threadRefButton))

  def isThreadRefButtonDisplayed: Boolean =
    driver.findElements(threadRefButton).asScala.nonEmpty &&
      getThreadRefButton.isDisplayed

  def isThreadRefButtonEnabled: Boolean =
    getThreadRefButton.isEnabled

  def getThreadRefButtonText: String =
    getThreadRefButton.getText.trim

  def selectThreadRefButton(): Unit =
    getThreadRefButton.click()

  def selectAcceptCookiesButton(): Unit =
    getAcceptCookiesButton.click()

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
