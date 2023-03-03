package ru.ssau.blockchaindocuments;

import io.reactivex.rxjava3.core.Flowable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import io.reactivex.rxjava3.disposables.Disposable;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

//@SpringBootApplication
public class BlockchainDocumentsApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(BlockchainDocumentsApplication.class, args);
        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/304127ae5cee4e06b114cb3d57749335"));


        EthGetBalance balance = web3j.ethGetBalance("0x578275e788B8e10f43A4b1F624b0007D930216Ce", DefaultBlockParameterName.LATEST).send();
        BigInteger wei = balance.getBalance();
        BigDecimal ether = Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
        System.out.println("Account balance: " + ether + " ETH");
    }

}
