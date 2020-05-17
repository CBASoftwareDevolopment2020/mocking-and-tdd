import pytest
import time
import json
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.desired_capabilities import DesiredCapabilities
from selenium.webdriver.common.alert import Alert

import unittest
from my_tests import *

class FrontendTest(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Chrome()
		self.path = "file:///C:/Users/Hupra/Desktop/slybank/updated3/mocking-and-tdd/test-client/index.html"
		self.driver.get("http://localhost:8080/TestingAssignmentW9_war_exploded/reset")

	def tearDown(self):
		self.driver.close()
		self.driver.quit()

	def test_add_bank_test(self):

		self.driver.get(self.path)
		self.driver.find_element_by_name("name").send_keys("test bank")
		self.driver.find_element_by_css_selector("input:nth-child(4)").send_keys("bitest")
		self.driver.find_element_by_css_selector("input:nth-child(5)").click()

		time.sleep(3);
		bank_list = self.driver.find_elements_by_class_name("item-container")

		list_container = bank_list[-1]
		spans = list_container.find_elements_by_tag_name("span");

		name = spans[0].text
		cvr = spans[1].text

		self.assertEqual(name, "Name test bank")
		self.assertEqual(cvr, "CVR bitest")

	def test_add_exisiting_bank(self):

		self.driver.get(self.path)
		self.driver.find_element_by_name("name").send_keys("test bank")
		self.driver.find_element_by_css_selector("input:nth-child(4)").send_keys("bid123")
		self.driver.find_element_by_css_selector("input:nth-child(5)").click()
		time.sleep(3)

		alert = self.driver.switch_to.alert
		text = alert.text
		alert.accept()

		assert text == "cvr exists"

	def test_get_customer(self):

		self.driver.get(self.path)
		self.driver.find_element_by_link_text("customers").click()
		self.driver.find_element_by_name("cpr").send_keys("id123")
		self.driver.find_element_by_css_selector("input:nth-child(3)").click()

		time.sleep(3)

		customers = self.driver.find_elements_by_class_name("item-container")
		customer = customers[-1]

		spans = customer.find_elements_by_tag_name("span")
		name = spans[0].text
		cpr = spans[1].text

		assert name == "Name Jacob"
		assert cpr == "CPR id123"

	def test_get_non_existing_customer(self):

		self.driver.get(self.path)
		self.driver.find_element_by_link_text("customers").click()
		self.driver.find_element_by_name("cpr").send_keys("noncus")
		self.driver.find_element_by_css_selector("input:nth-child(3)").click()

		time.sleep(3)

		alert = self.driver.switch_to.alert
		text = alert.text
		alert.accept()

		assert text == "some error"

	def test_add_movement(self):

		success = False

		self.driver.get(self.path)
		self.driver.find_element_by_link_text("movements").click()
		self.driver.find_element_by_name("amount").send_keys("420")
		self.driver.find_element_by_name("source").send_keys("42069")
		self.driver.find_element_by_name("target").send_keys("src123")

		self.driver.find_element_by_css_selector("input:nth-child(7)").click()

		time.sleep(4)

		items = self.driver.find_elements_by_css_selector(".item")

		for item in items:
		    spans = item.find_elements_by_tag_name("span")
		    
		    if spans[0].text != "Source 42069": continue
		    if spans[1].text != "Target src123": continue
		    if spans[3].text != "Amount 420": continue
		    
		    success = True
		    
		assert success is True


	def test_add_invalid_movement(self):

		self.driver.get(self.path)
		self.driver.find_element_by_link_text("movements").click()
		self.driver.find_element_by_name("amount").send_keys("420")
		self.driver.find_element_by_name("source").send_keys("invalid")
		self.driver.find_element_by_name("target").send_keys("invalid2")

		self.driver.find_element_by_css_selector("input:nth-child(7)").click()

		time.sleep(3)

		alert = self.driver.switch_to.alert
		text = alert.text
		alert.accept()

		assert text == "some error"


if __name__ == '__main__':
	unittest.main()