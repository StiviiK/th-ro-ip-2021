import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

describe('Testing Login', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Should login.', async () => {
    await page.navigateTo();
    await page.performLogin();
    expect(await page.getMyPapersButton()).toEqual('My Papers');
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
