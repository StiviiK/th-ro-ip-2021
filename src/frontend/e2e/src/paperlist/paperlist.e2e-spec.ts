import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

/**
 * Tests for the list containing all papers of an user.
 * @author Lukas Metzner
 */
describe('Testing My Papers', () => {
  let page: AppPage;

  /**
   * Naviagte to app and perform a login.
   */
  beforeAll(async () => {
    page = new AppPage();
    await page.navigateTo();
    await page.performLogin();
  });

  /**
   * After using the "Add Paper" dialog to add a new paper
   * the list of papers should be increased by one.
   */
  it('Should add paper.', async () => {
    let beforeCount = await page.getPaperCount();
    await page.addPaper();
    let afterCount = await page.getPaperCount();
    expect(afterCount - beforeCount).toBe(1);
  });

  /**
   * After deleting a single paper from the list the amount
   * of papers should be decreased by one.
   */
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
      } as logging.Entry),
    );
  });

  /**
   * Log out after all tests are done.
   */
  afterAll(async () => {
    await page.performLogout();
  });
});
