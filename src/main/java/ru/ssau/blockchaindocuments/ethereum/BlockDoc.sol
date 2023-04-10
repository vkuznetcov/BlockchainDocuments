pragma solidity ^0.8.0;

contract DocumentRegistry {
    struct Document {
        bytes32 hash;
        string metadata;
        bool exists;
    }

    mapping(bytes32 => Document) private documents;

    event DocumentAdded(bytes32 indexed hash, string metadata);
    event DocumentDeleted(bytes32 indexed hash);

    function addDocument(bytes32 hash, string memory metadata) public {
        require(!documents[hash].exists, "Document already exists");

        documents[hash] = Document(hash, metadata, true);
        emit DocumentAdded(hash, metadata);
    }

    function verifyDocument(bytes32 hash, string memory metadata) public view returns (bool) {
        Document storage doc = documents[hash];
        return doc.exists && keccak256(abi.encodePacked(doc.metadata)) == keccak256(abi.encodePacked(metadata));
    }

    function deleteDocument(bytes32 hash) public {
        require(documents[hash].exists, "Document does not exist");

        delete documents[hash];
        emit DocumentDeleted(hash);
    }
}