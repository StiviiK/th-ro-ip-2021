import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

/**
 * Test if login dialog works.
 * 
 * @author Lukas Metzner
 */
describe('Testing Login', () => {
  let page: AppPage;

  /**
   * Create new instance of the page.
   */
  beforeEach(() => {
    page = new AppPage();
  });

  /**
   * Navigate to the page and perform the login.
   */
  it('Should login.', async () => {
    await page.navigateTo();
    await page.performLogin();
    expect(await page.getMyPapersButton()).toEqual('My Papers');
  });

  /**
   * After tests -> logout.
   */
  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    await page.performLogout();
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
