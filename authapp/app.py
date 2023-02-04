from flask import Flask
from flask_migrate import Migrate
from src.database.models import db, User
from src.utlis.jwt_utils import token_required
from src.controller.auth_controller import AuthController

from dotenv import load_dotenv
import os

load_dotenv()

app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = os.getenv("SQLALCHEMY_DATABASE_URI")
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = os.getenv("SQLALCHEMY_TRACK_MODIFICATIONS")
app.config["JWT_SECRET_KEY"] = os.getenv("JWT_SECRET_KEY")

db.init_app(app)
@app.before_first_request
def create_tables():
    db.create_all()

migrate = Migrate(app, db, include_schemas=True)


auth_controller = AuthController()


# get user detail route
@app.route("/auth/user", methods=["GET"])
@token_required
def get_user_detail(current_user):
    return auth_controller.get_user_detail(current_user)

# login route
@app.route("/auth/login", methods=["POST"])
def login():
    return auth_controller.login()

# register route
@app.route("/auth/register", methods=["POST"])
def signup():
    return auth_controller.signup()


if __name__ == "__main__":
    app.run()
