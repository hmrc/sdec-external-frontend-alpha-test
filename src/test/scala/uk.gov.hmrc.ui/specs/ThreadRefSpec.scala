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

package uk.gov.hmrc.ui.specs

import org.openqa.selenium.support.ui.{ExpectedConditions, WebDriverWait}
import org.scalatest.featurespec.AnyFeatureSpec

import uk.gov.hmrc.ui.pages.AuthLoginPage
import uk.gov.hmrc.ui.pages.ThreadReferencePage
import uk.gov.hmrc.ui.specs.tags.AcceptanceTests

class ThreadRefSpec extends BaseSpec {

  Feature("External User Journey - Thread Reference") {

    Scenario("Thread Reference page display", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the Enter thread reference page loads")

      Then("the system must display the input field and continue button")

      ThreadReferencePage.getThreadReferenceText shouldBe "Enter the thread reference number"

      val inputDisplayed  = ThreadReferencePage.isThreadReferenceInputDisplayed
      val buttonDisplayed = ThreadReferencePage.isContinueButtonDisplayed
      val buttonEnabled   = ThreadReferencePage.isContinueButtonEnabled
      val buttonText      = ThreadReferencePage.getContinueButtonText
      val captionText     = ThreadReferencePage.getCaptionText

      inputDisplayed shouldBe true

      And("the system must display the service caption")
      captionText shouldBe "Share Files Securely with HMRC"

      And("the system must display a continue button")
      buttonDisplayed shouldBe true

      And("the button must be selectable")
      buttonEnabled shouldBe true

      And("the button must follow GOV.UK Design System standards")
      buttonText shouldBe "Continue"
    }

    Scenario("Empty field validation", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page")

      And("the user clicks Continue without entering a value")

      ThreadReferencePage.selectContinueButton()

      Then("the system must prevent progression")

      val errorSummary = ThreadReferencePage.isErrorTitleDisplayed
      val errorTitle   = ThreadReferencePage.getErrorTitleText
      val errorText    = ThreadReferencePage.getThreadReferenceText
      And("the system must display an error summary at the top of the page")
      errorSummary shouldBe true
      errorTitle     should include("There is a problem")

      And("""the system must display the error message "Enter the thread reference number"""")
      errorText should include("Enter the thread reference number")
    }

    Scenario("Invalid Format", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page")
      ThreadReferencePage.enterThreadReference("ABCD")

      And("the user clicks Continue button")

      ThreadReferencePage.selectContinueButton()

      Then("the system must prevent progression and display error message")

      ThreadReferencePage.isThreadRefUnsuccessful should include(
        "The thread reference contains 12 characters using A - Z and 0 - 9 only"
      )

    }

    Scenario("Input field rules successful validation", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page and keys the thread reference number")
      ThreadReferencePage.enterThreadReference("123456ABCDEF")

      And("the user clicks Continue button")

      ThreadReferencePage.selectContinueButton()

      Then("the system must validate the manual entry with 12 characters")
      ThreadReferencePage.isThreadRefSuccessful should include("THREAD-001")

    }

    Scenario("Input field rules error validation", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page and keys the thread reference number")
      ThreadReferencePage.enterThreadReference("ABCD$%&cv")

      And("the user clicks Continue button")

      ThreadReferencePage.selectContinueButton()

      Then("the system must validate the entry with special characters and case sensitive")

      ThreadReferencePage.isThreadRefUnsuccessful should include(
        "The thread reference contains 12 characters using A - Z and 0 - 9 only"
      )

    }

  }
}
