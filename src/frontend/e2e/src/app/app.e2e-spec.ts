import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

/**
 * Test if page loads correctly by testing the navbar title.
 * 
 * @author Lukas Metzner
 */
describe('Testing Title', () => {
  let page: AppPage;

  /**
   * Create an instance of the page.
   */
  beforeEach(() => {
    page = new AppPage();
  });

  /**
   * Correct title should be in navbar.
   */
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
