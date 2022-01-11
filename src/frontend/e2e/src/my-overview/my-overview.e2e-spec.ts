import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

/**
 * Tests for the my-overview page.
 * @author Alessandro Soro
 */
describe('Testing My Overview', () => {
  let page: AppPage;

  /**
   * Navigates to page, logs in and navigates to my papers page.
   * Adds a single paper to the list and navigates to my-overview page.
   */
  beforeAll(async () => {
    page = new AppPage();
    await page.navigateTo();
    await page.performLogin();
    await page.navigateToMyPapers();
    await page.addPaper();
    await page.navigateToMyOverview();
  });

  /**
   * Tests if liking a paper works.
   * Checks amount of paper bevor liking a paper and after
   * and checks if the amount is correct after the paper is liked.
   */
  it('Should like paper.', async () => {
    let beforeCountLikeable = await page.getLikeablePapersCount();
    let beforeCountLiked = await page.getLikedPapersCount();
    await page.likePaper();
    let afterCountLikeable = await page.getLikeablePapersCount();
    let afterCountLiked = await page.getLikedPapersCount();
    expect(afterCountLikeable - beforeCountLikeable).toBe(-1);
    expect(afterCountLiked - beforeCountLiked).toBe(1);
  });

  /**
   * Tests if removing a liked paper works.
   * Checks amount of paper bevor removing a liked paper and after
   * and checks if the amount is correct after the paper is removed.
   */
  it('Should remove liked paper.', async () => {
    let beforeCountLikeable = await page.getLikeablePapersCount();
    let beforeCountLiked = await page.getLikedPapersCount();
    await page.removeLikedPaper();
    let afterCountLikeable = await page.getLikeablePapersCount();
    let afterCountLiked = await page.getLikedPapersCount();
    expect(afterCountLikeable - beforeCountLikeable).toBe(1);
    expect(afterCountLiked - beforeCountLiked).toBe(-1);
  });

  /**
   * Logger for after every test.
   */
  afterEach(async () => {
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(
      jasmine.objectContaining({
        level: logging.Level.SEVERE,
      } as logging.Entry),
    );
  });

  /**
   * After every test navigates to my papers page and removes added paper
   * and sign out of the app.
   */
  afterAll(async () => {
    await page.navigateToMyPapers();
    await page.deletePaper();
    await page.performLogout();
  });
});
