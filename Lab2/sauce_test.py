from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.options import Options
import unittest
import logging
import os

class TestSauce(unittest.TestCase):

    homedir = os.path.expanduser("~")
    webdriver_service = Service(f"{homedir}/chromedriver/stable/chromedriver")

    def setUp(self):
        self.driver = None
        logging.info("Zainicjalizowano testy")

    def tearDown(self):
        logging.info("Zakończono testy")
        if self.driver:
            self.driver.quit()

    def test_login(self):
        chrome_options = Options()
        chrome_options.add_argument("--headless")
        chrome_options.add_argument("--no-sandbox")

        browsers_to_test = [
            ('Chrome', webdriver.Chrome(options=chrome_options, service=self.webdriver_service)),
            ('Firefox', webdriver.Firefox)
        ]

        logging.info("Rozpoczęto test test_login")

        for browser_name, browser in browsers_to_test:
            with self.subTest(browser_name=browser_name):
                self.driver = browser()
                self.driver.maximize_window()
                self.driver.implicitly_wait(10)

                self.driver.get('https://www.saucedemo.com/')

                wait = WebDriverWait(self.driver, 10)
                user_name_field = wait.until(EC.presence_of_element_located((By.ID, 'user-name')))
                user_name_field.click()
                user_name_field.send_keys("standard_user")

                password_field = wait.until(EC.presence_of_element_located((By.ID, 'password')))
                password_field.click()
                password_field.send_keys("secret_sauce")

                button_login = wait.until(EC.element_to_be_clickable((By.ID, 'login-button')))
                button_login.click()
                current_url = self.driver.current_url
                self.assertEqual("https://www.saucedemo.com/inventory.html", current_url, "niepoprawny URL")

                self.driver.quit()

if __name__ == "__main__":
    unittest.main()
