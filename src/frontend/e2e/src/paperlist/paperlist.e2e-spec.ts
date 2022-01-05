import { AppPage } from '../app.po';
import { browser, element, logging } from 'protractor';
import { By } from 'selenium-webdriver';

describe('Testing My Papers', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it("Should add paper.", async () => {
    await page.navigateTo();
    await page.performLogin();
    let beforeCount = await page.getPaperCount();
    await page.addPaper();
    let newCount = await page.getPaperCount();
    expect(newCount - beforeCount).toBe(1);
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    await page.performLogout();
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
