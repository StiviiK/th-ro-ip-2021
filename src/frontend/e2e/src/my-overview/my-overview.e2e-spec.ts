import { AppPage } from '../app.po';
import { browser, logging } from 'protractor';

describe('Testing My Overview', () => {
  let page: AppPage;

  beforeAll (async () => {
    page = new AppPage();
    await page.navigateTo();
    await page.performLogin();
    await page.navigateToMyPapers();
    await page.addPaper();
    await page.navigateToMyOverview();
  });

  it("Should like paper.", async () => {
    let beforeCountLikeable = await page.getLikeablePapersCount();
    let beforeCountLiked = await page.getLikedPapersCount();
    await page.likePaper();
    let afterCountLikeable = await page.getLikeablePapersCount();
    let afterCountLiked = await page.getLikedPapersCount();
    expect(afterCountLikeable - beforeCountLikeable).toBe(-1);
    expect(afterCountLiked - beforeCountLiked).toBe(1);
  });

  it("Should remove liked paper.", async () => {
    let beforeCountLikeable = await page.getLikeablePapersCount();
    let beforeCountLiked = await page.getLikedPapersCount();
    await page.removeLikedPaper();
    let afterCountLikeable = await page.getLikeablePapersCount();
    let afterCountLiked = await page.getLikedPapersCount();
    expect(afterCountLikeable - beforeCountLikeable).toBe(1);
    expect(afterCountLiked - beforeCountLiked).toBe(-1);
  });

  afterEach(async () => {
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });

  afterAll (async () =>{
    await page.navigateToMyPapers();
    await page.deletePaper();
    await page.performLogout();
  });
});
