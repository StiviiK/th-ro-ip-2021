import { AppPage } from './app.po';
import { browser, logging } from 'protractor';

describe('title', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('Should display navbar title.', async () => {
    await page.navigateTo();
    expect(await page.getTitleText()).toEqual('Scientific Paper Management');
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
