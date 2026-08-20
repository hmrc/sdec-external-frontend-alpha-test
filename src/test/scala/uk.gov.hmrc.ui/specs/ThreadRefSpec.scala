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
      ThreadReferencePage.getThreadRefPageName         should include("Share Files Securely with HMRC")
      ThreadReferencePage.selectAcceptCookiesButton()
      ThreadReferencePage.isThreadRefButtonDisplayed shouldBe true
      ThreadReferencePage.isThreadRefButtonEnabled   shouldBe true
      ThreadReferencePage.selectThreadRefButton()

      Then("the system must display the input field and continue button")
      ThreadReferencePage.getThreadReferenceText          shouldBe "Enter the thread reference number"
      ThreadReferencePage.isThreadReferenceInputDisplayed shouldBe true

      And("the system must display the service caption")
      ThreadReferencePage.getCaptionText shouldBe "Share Files Securely with HMRC"

      And("the system must display a continue button")
      ThreadReferencePage.isContinueButtonDisplayed shouldBe true

      And("the button must be selectable")
      ThreadReferencePage.isContinueButtonEnabled shouldBe true

      And("the button must follow GOV.UK Design System standards")
      ThreadReferencePage.getContinueButtonText shouldBe "Continue"
    }

    Scenario("Empty field validation", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page")
      ThreadReferencePage.selectThreadRefButton()

      And("the user clicks Continue without entering a value")
      ThreadReferencePage.selectContinueButton()

      Then("the system must display an error summary at the top of the page")
      ThreadReferencePage.getErrorTitleText should include("There is a problem")

      And("""the system must display the error message "Enter the thread reference number"""")
      ThreadReferencePage.getThreadReferenceText should include("Enter the thread reference number")
    }

    Scenario("Invalid Format", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the user navigates to the thread reference page")
      ThreadReferencePage.selectThreadRefButton()
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
      ThreadReferencePage.selectThreadRefButton()
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
      ThreadReferencePage.selectThreadRefButton()
      ThreadReferencePage.enterThreadReference("ABCD$%&cv")

      And("the user clicks Continue button")
      ThreadReferencePage.selectContinueButton()

      Then("the system must validate the entry with special characters and case sensitive")
      ThreadReferencePage.isThreadRefUnsuccessful should include(
        "The thread reference contains 12 characters using A - Z and 0 - 9 only"
      )

    }

    Scenario("Authenticated user login ", AcceptanceTests) {

      Given("User logs in")
      AuthLoginPage.login()

      When("the page loads with the url by authenticated user")
      ThreadReferencePage.selectThreadRefButton()

      Then("the page must display Enter the thread reference number")
      ThreadReferencePage.getThreadReferenceText shouldBe "Enter the thread reference number"
    }

    Scenario("Unauthenticated user login ", AcceptanceTests) {

      Given("User logs in with the given url")
      AuthLoginPage.authIdent()

      When("the page loads with the given url by unauthenticated user")

      Then("the url must redirect the authorisation page")

      ThreadReferencePage.getRedirectPageText should include("Authority Wizard")
    }

  }
}
