from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
import unittest
import logging
import os

class TestCocoa(unittest.TestCase):

    homedir = os.path.expanduser("~")
    webdriver_service = Service(f"{homedir}/chromedriver/stable/chromedriver")

    def setUp(self):
        self.driver = None
        logging.info("Zainicjalizowano testy")

    def tearDown(self):
        logging.info("Zakończono testy")
        if self.driver:
            self.driver.quit()

    def test_cocoa(self):
        chrome_options = Options()
        chrome_options.add_argument("--headless")
        chrome_options.add_argument("--no-sandbox")

        browsers_to_test = [
            ('Chrome', webdriver.Chrome(options=chrome_options, service=self.webdriver_service)),
            ('Firefox', webdriver.Firefox)
        ]

        logging.info("Rozpoczęto test test_cocoa")

        for browser_name, browser in browsers_to_test:
            with self.subTest(browser_name=browser_name):
                self.driver = browser()
                self.driver.maximize_window()
                self.driver.implicitly_wait(10)

                self.driver.get('https://pluskakao.pl/')

                wait = WebDriverWait(self.driver, 10)
                shop = wait.until(EC.find_element_by_css_selector("[href='https://pluskakao.pl/sklep-kakao/']"))
                shop.click()

                cocoa_field = wait.until(EC.find_element_by_css_selector("[href='https://pluskakao.pl/produkt/kakao-wenezuelski-mistyk/']"))
                cocoa_field.click()

                add_to_cart = wait.until(EC.presence_of_element_located((By.TAG_NAME, 'single_add_to_cart_button')))
                add_to_cart.click()
                
                bdi_tag = wait.until(EC.presence_of_element_located((By.TAG_NAME, 'bdi')))
                self.assertIn('100,00', bdi_tag.text, "Wartość koszyka nie wynosi 100 zł'")

                self.driver.quit()

if __name__ == "__main__":
    unittest.main()
