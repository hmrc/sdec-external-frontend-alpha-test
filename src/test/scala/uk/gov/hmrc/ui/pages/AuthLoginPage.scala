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

import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.{By, WebDriver}
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.driver.BrowserDriver

import java.time.Duration

object AuthLoginPage extends BrowserDriver with BasePage {

  private val wait = new WebDriverWait(driver, Duration.ofSeconds(10))

  val url: String         = s"${TestConfiguration.url("auth-login-stub")}/gg-sign-in"
  val frontEndUrl: String = TestConfiguration.url("sdec-external-frontend-alpha")

  object Fields {
    val credId: By      = By.id("authorityId")
    val redirectUrl: By = By.id("redirectionUrl")
  }

  private val redirectUrls: Map[String, String] = Map(
    "sdec-external-frontend-alpha" -> frontEndUrl
  )

  private def resolveRedirect(page: String): String =
    redirectUrls.getOrElse(
      page,
      throw new IllegalArgumentException(s"Unknown redirect page: $page")
    )

  def login(
    credId: String = "258798531149531"
  ): Unit = {
    navigateTo(url)
    sendKeys(Fields.credId, credId)
    sendKeys(Fields.redirectUrl, resolveRedirect("sdec-external-frontend-alpha"))
    continue()
  }
}
