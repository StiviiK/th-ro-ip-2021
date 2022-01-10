import { browser, by, element } from 'protractor';
export class AppPage {
  paperUrl: string = "https://arxiv.org/pdf/1906.01502.pdf";

  async navigateTo(): Promise<unknown> {
    return browser.get(browser.baseUrl);
  }

  async navigateToMyOverview(): Promise<unknown> {
    return element(by.id("my-overview-button")).click();
  }

  async navigateToMyPapers(): Promise<unknown> {
    return element(by.id("mypapers-button")).click();
  }

  async getTitleText(): Promise<string> {
    return element(by.id('navbar-title')).getText();
  }

  async performLogin(): Promise<unknown> {
    await element(by.id("username-input")).sendKeys("foo");
    await element(by.id("password-input")).sendKeys("foo");
    return element(by.id("login-button")).click();
  }

  async performLogout(): Promise<unknown> {
    return element(by.id("logout-button")).click();
  }

  async getMyPapersButton(): Promise<string> {
    return element(by.id("mypapers-button")).getText();
  }

  async addPaper(): Promise<unknown> {
    await element(by.id("add-paper-button")).click();
    await element(by.id("paper-url-input")).sendKeys(this.paperUrl);
    await element(by.id("submit-paper-button")).click();
    return setTimeout(() => {}, 10000);
  }

  async likePaper(): Promise<unknown> {
    await element
    .all(by.css(".likeable-list-container"))
    .get(0)
    .click();
    await element(by.id("like-paper-button")).click();
    return setTimeout(() => {}, 10000);
  }

  async removeLikedPaper(): Promise<unknown> {
    await element
    .all(by.css(".liked-list-container"))
    .get(0)
    .click();
    await element(by.id("remove-liked-paper-button")).click();
    return setTimeout(() => {}, 10000);
  }

  async deletePaper(): Promise<unknown> {
    await element
    .all(by.css(".list-container"))
    .get(0)
    .element(by.css(".deleteIcon"))
    .click();
    return setTimeout(() => {}, 5000);
  }

  async getPaperCount(): Promise<number> {
    return element.all(by.css(".list-container")).count();
  }

  async getLikedPapersCount(): Promise<number> {
    return element.all(by.css(".liked-list-container")).count();
  }

  async getLikeablePapersCount(): Promise<number> {
    return element.all(by.css(".likeable-list-container")).count();
  }
}
