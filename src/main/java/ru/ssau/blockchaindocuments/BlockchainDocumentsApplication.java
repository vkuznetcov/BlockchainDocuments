package ru.ssau.blockchaindocuments;

import io.reactivex.rxjava3.core.Flowable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import io.reactivex.rxjava3.disposables.Disposable;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.utils.Convert;
import ru.ssau.blockchaindocuments.ethereum.consts.EthereumConsts;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

//@SpringBootApplication
public class BlockchainDocumentsApplication {

    public static void main(String[] args) throws Exception {

        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/304127ae5cee4e06b114cb3d57749335"));


        Credentials credentials = Credentials.create(EthereumConsts.METAMASK_PRIVATE_KEY);

        BlockDoc contract = BlockDoc.load(EthereumConsts.CONTRACT_ADDRESS, web3j, credentials, BigInteger.valueOf(300000), BigInteger.valueOf(300000));
        System.out.println("document loaded");

//        SpringApplication.run(BlockchainDocumentsApplication.class, args);

//        EthGetBalance balance = web3j.ethGetBalance("0x578275e788B8e10f43A4b1F624b0007D930216Ce", DefaultBlockParameterName.LATEST).send();
//        BigInteger wei = balance.getBalance();
//        BigDecimal ether = Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
//        System.out.println("Account balance: " + ether + " ETH");

        byte[] document = "My document".getBytes();
        String metadata = "Document metadata";
        byte[] hash = Hash.sha3(document);
        System.out.println("sha calculated");

        BigInteger id = BigInteger.valueOf(1);
        TransactionReceipt receipt = contract.addDocument(hash, metadata).send();
        System.out.println("method store");
        System.out.println(receipt.toString());

        BigInteger id1 = BigInteger.valueOf(1);
        Boolean result = contract.verifyDocument(hash, metadata).send();
        System.out.println("method get");

        if (result) {
            System.out.println("Document is verified!");
        } else {
            System.out.println("Document verification failed.");
        }
    }

}
