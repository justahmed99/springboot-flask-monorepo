from flask import Flask, request, jsonify, make_response
from src.service.user_service import UserRepository, UserService
from src.service.jwt_service import JwtRepository, JwtService

user_repo = UserRepository()
user_service = UserService(user_repo)

jwt_repo = JwtRepository()
jwt_service = JwtService(jwt_repo)

class AuthController:
    def get_user_detail(self, current_user):
        return jsonify(
            {
                "user": {
                    "name": current_user.name,
                    "phone": current_user.phone,
                    "role": current_user.role,
                    "timestamp": int(current_user.registry_iat_datetime.timestamp() * 1000)
                }
            }
        )

    def login(self):
        auth = request.get_json()

        if not auth or not auth["phone"] or not auth["password"]:
            return make_response(
                jsonify({"message": "phone number and password is required!"}), 400
            )

        user = user_service.get_user_by_phone_without_token(auth["phone"])

        if not user:
            return make_response(
                jsonify({"message": "incorrect phone number or password!"}), 403
            )

        status, token = jwt_service.generate_token(user, auth["password"])
        if status:
            return make_response(jsonify({"token": token.encode().decode("utf-8")}), 200)

        return make_response(
            jsonify({"message": "incorrect phone number or password!"}), 403
        )

    def signup(self):
        data = request.get_json()

        status, password = user_service.register(data)

        if status:
            return make_response(jsonify({"password": password}), 201)
        else:
            return make_response(
                jsonify({"message": "Your registration is still valid. Please Log in."}), 202
            )