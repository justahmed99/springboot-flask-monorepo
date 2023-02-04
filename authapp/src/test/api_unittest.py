import unittest
import json
class ApiUnitTest(unittest.TestCase):
    def test_get_user_detail(self):
        response = self.app.get("/auth/user", headers={'Authorization': 'Bearer blablabla'})
        self.assertEqual(response.status_code, 401)
    
    def test_login(self):
        response = self.app.post("/auth/login", data=json.dumps({
            'phone': '+6285317727117',
            'password': 'nWnq'
        }), content_type='application/json')
        self.assertEqual(response.status_code, 403)

    def test_register(self):
        response = self.app.post("/auth/register", data=json.dumps({
            "name": "Ahmad Mujahid Abdurrahman",
            "phone": "+6285317727117",
            "role": "member"
        }), content_type='application/json')
        self.assertEqual(response.status_code, 400)

if __name__ == '__main__':
    unittest.main()