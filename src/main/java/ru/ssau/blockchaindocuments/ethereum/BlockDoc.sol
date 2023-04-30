pragma solidity ^0.8.0;

contract DocumentRegistry {
    struct Document {
        bytes32 hash;
        string metadata;
        bool exists;
        uint256 addedBy;
    }

    mapping(bytes32 => Document) private documents;

    event DocumentAdded(bytes32 indexed hash, string metadata, uint256 addedBy);
    event DocumentDeleted(bytes32 indexed hash);

    function addDocument(bytes32 hash, string memory metadata, uint256 addedBy) public {
        require(!documents[hash].exists, "Document already exists");

        documents[hash] = Document(hash, metadata, true, addedBy);
        emit DocumentAdded(hash, metadata, addedBy);
    }

    function verifyDocument(bytes32 hash, string memory metadata) public view returns (bool) {
        Document storage doc = documents[hash];
        return doc.exists && keccak256(abi.encodePacked(doc.metadata)) == keccak256(abi.encodePacked(metadata));
    }

    function deleteDocument(bytes32 hash, uint256 userId) public {
        Document storage doc = documents[hash];
        require(doc.exists, "Document does not exist");
        require(doc.addedBy == userId, "User is not authorized to delete this document");

        delete documents[hash];
        emit DocumentDeleted(hash);
    }
}