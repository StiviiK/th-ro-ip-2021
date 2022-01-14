import { browser, by, element } from 'protractor';

/**
 * Tests for the my-overview page.
 * @author Alessandro Soro
 * @author Lukas Metzner
 */
export class AppPage {
  paperUrl: string = 'https://arxiv.org/pdf/1906.01502.pdf';

  /**
   * Navigates to the base Url of the app.
   * @returns Promise of the action.
   */
  async navigateTo(): Promise<unknown> {
    return browser.get(browser.baseUrl);
  }

  /**
   * Navigates to my overview page.
   * @returns Promise of the action.
   */
  async navigateToMyOverview(): Promise<unknown> {
    return element(by.id('my-overview-button')).click();
  }

  /**
   * Navigates to my papers page.
   * @returns Promise of the action.
   */
  async navigateToMyPapers(): Promise<unknown> {
    return element(by.id('mypapers-button')).click();
  }

  /**
   * Getter of title text of the app.
   * @returns Promise including the text.
   */
  async getTitleText(): Promise<string> {
    return element(by.id('navbar-title')).getText();
  }

  /**
   * Logs into the app.
   * @returns Promise of the action.
   */
  async performLogin(): Promise<unknown> {
    await element(by.id('username-input')).sendKeys('foo');
    await element(by.id('password-input')).sendKeys('foo');
    return element(by.id('login-button')).click();
  }

  /**
   * Logs out of the app.
   * @returns Promise of the action.
   */
  async performLogout(): Promise<unknown> {
    return element(by.id('logout-button')).click();
  }

  async getMyPapersButton(): Promise<string> {
    return element(by.id('mypapers-button')).getText();
  }

  /**
   * Adds the Paper to the list of papers.
   * @returns Timeout.
   */
  async addPaper(): Promise<unknown> {
    await element(by.id('add-paper-button')).click();
    await element(by.id('paper-url-input')).sendKeys(this.paperUrl);
    await element(by.id('submit-paper-button')).click();
    return setTimeout(function () {
      // This is intentional
    }, 10000);
  }

  /**
   * Gets the first entry of the list in the my-overview page
   * and presses the button to like a paper.
   * @returns Timeout.
   */
  async likePaper(): Promise<unknown> {
    await element.all(by.css('.likeable-list-container')).get(0).click();
    await element(by.id('like-paper-button')).click();
    return setTimeout(function () {
      // This is intentional
    }, 10000);
  }

  /**
   * Gets the list of liked papers in the my-overview page
   * and presses on the button to remove the liked paper.
   * @returns Timeout.
   */
  async removeLikedPaper(): Promise<unknown> {
    await element.all(by.css('.liked-list-container')).get(0).click();
    await element(by.id('remove-liked-paper-button')).click();
    return setTimeout(function () {
      // This is intentional
    }, 10000);
  }

  /**
   * Gets the first entry of the liked list in the
   * my papers page and presses on the delete icon.
   * @returns Timeout.
   */
  async deletePaper(): Promise<unknown> {
    await element
      .all(by.css('.list-container'))
      .get(0)
      .element(by.css('.deleteIcon'))
      .click();
    return setTimeout(function () {
      // This is intentional
    }, 5000);
  }

  /**
   * Gets the amount of papers in the list on
   * the my papers page.
   * @returns Amount of papers.
   */
  async getPaperCount(): Promise<number> {
    return element.all(by.css('.list-container')).count();
  }

  /**
   * Gets the amount of papers in the liked list on
   * the my overview page.
   * @returns Amount of papers.
   */
  async getLikedPapersCount(): Promise<number> {
    return element.all(by.css('.liked-list-container')).count();
  }

  /**
   * Gets the amount of papers in the list on
   * the my papers page.
   * @returns Amount of papers.
   */
  async getLikeablePapersCount(): Promise<number> {
    return element.all(by.css('.likeable-list-container')).count();
  }
}
