import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

describe('Testing My Papers', () => {
  let page: AppPage;
  beforeAll(async () => {
    page = new AppPage();
    await page.navigateTo();
    await page.performLogin();
  });

  it('Should add paper.', async () => {
    let beforeCount = await page.getPaperCount();
    await page.addPaper();
    let afterCount = await page.getPaperCount();
    expect(afterCount - beforeCount).toBe(1);
  });

  it('Should delete paper.', async () => {
    let beforeCount = await page.getPaperCount();
    await page.deletePaper();
    let afterCount = await page.getPaperCount();
    expect(afterCount - beforeCount).toBe(-1);
  });

  afterEach(async () => {
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(
      jasmine.objectContaining({
        level: logging.Level.SEVERE,
      } as logging.Entry)
    );
  });

  afterAll(async () => {
    await page.performLogout();
  });
});
