pragma solidity ^0.8.0;

contract UserRegistry {
    struct User {
        string login;
        string password;
        uint256 id;
    }

    mapping(uint256 => User) private users;

    uint256 private lastId = 0;
    string constant private AUTH_KEY = "ieyeep6Ew0aiwu2ooCae";

    event UserAdded(uint256 indexed userId, string login, string password);

    function addUser(string memory _login, string memory _password, string memory _authKey) public {
        require(keccak256(abi.encodePacked(_authKey)) == keccak256(abi.encodePacked(AUTH_KEY)), "Invalid auth key");

        uint256 userId = lastId + 1;
        users[userId] = User(_login, _password, userId);
        lastId = userId;

        emit UserAdded(userId, _login, _password);
    }

    function getUserId(string memory _login, string memory _password) public view returns (uint256) {
        for (uint256 i = 1; i <= lastId; i++) {
            if (keccak256(abi.encodePacked(users[i].login)) == keccak256(abi.encodePacked(_login)) &&
                keccak256(abi.encodePacked(users[i].password)) == keccak256(abi.encodePacked(_password))) {
                return users[i].id;
            }
        }
        revert("User not found");
    }

    function deleteUser(uint256 _userId, string memory _authKey) public {
        require(keccak256(abi.encodePacked(_authKey)) == keccak256(abi.encodePacked(AUTH_KEY)), "Invalid auth key");
        require(_userId > 0 && _userId <= lastId, "User not found");

        delete users[_userId];
    }
}