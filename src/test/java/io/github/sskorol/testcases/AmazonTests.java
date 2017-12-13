package io.github.sskorol.testcases;

import io.github.sskorol.data.Data;
import io.github.sskorol.data.DataSuppliers;
import io.github.sskorol.model.Account;
import io.github.sskorol.model.Parfume;
import io.github.sskorol.pages.LoginPage;
import io.github.sskorol.pages.ProductPage;
import io.github.sskorol.pages.SearchPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.github.sskorol.core.PageFactory.at;
import static io.github.sskorol.core.PageFactory.open;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Amazon.com page.
 */

public class AmazonTests {

    @Data(source = "parfume.json", entity = Parfume.class)
    @Data(source = "accountAmazon.json", entity = Account.class)
    @Test(dataProvider = "getDataCollection", dataProviderClass = DataSuppliers.class)
    @Feature("Product search")
    @Story("Implement search functionality")
    @Issue("35")
    @TmsLink("35")
    @Severity(SeverityLevel.BLOCKER)
    public void shouldSearchForParfume(final Parfume parfume, final Account account) {

        open(LoginPage.class)
                .login(account.getUsername(), account.getPassword());

        assertThat(at(LoginPage.class).getLoginStatus()).isEqualTo("Authorized successfully");

        at(SearchPage.class)
                .searchFor(parfume.getName());

        at(ProductPage.class)
                .selectCategoryBy(parfume.getSubCategory())
                .selectCheckboxBy(parfume.getSize())
                .selectProduct()
                .selectScent(parfume.getScent())
                .buy();

        assertThat(at(ProductPage.class).getPurchaseStatus()).isEqualTo("Operation was successfully completed");
    }
}