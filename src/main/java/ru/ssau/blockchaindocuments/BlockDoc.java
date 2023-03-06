package ru.ssau.blockchaindocuments;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.2.
 */
@SuppressWarnings("rawtypes")
public class BlockDoc extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5061095f806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c806376c3878c1461003b578063d602280d1461006b575b600080fd5b610055600480360381019061005091906103b5565b610087565b604051610062919061042c565b60405180910390f35b610085600480360381019061008091906103b5565b610113565b005b60008060008085815260200190815260200160002090508060020160009054906101000a900460ff16801561010a5750826040516020016100c891906104b8565b60405160208183030381529060405280519060200120816001016040516020016100f291906105c7565b60405160208183030381529060405280519060200120145b91505092915050565b60008083815260200190815260200160002060020160009054906101000a900460ff1615610176576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161016d9061063b565b60405180910390fd5b6040518060600160405280838152602001828152602001600115158152506000808481526020019081526020016000206000820151816000015560208201518160010190816101c591906107fc565b5060408201518160020160006101000a81548160ff021916908315150217905550905050817f640aef29e99d3682bbe380b03b22038c2ea6acc3434e53f9166b9e0d35260944826040516102199190610907565b60405180910390a25050565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b61024c81610239565b811461025757600080fd5b50565b60008135905061026981610243565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6102c282610279565b810181811067ffffffffffffffff821117156102e1576102e061028a565b5b80604052505050565b60006102f4610225565b905061030082826102b9565b919050565b600067ffffffffffffffff8211156103205761031f61028a565b5b61032982610279565b9050602081019050919050565b82818337600083830152505050565b600061035861035384610305565b6102ea565b90508281526020810184848401111561037457610373610274565b5b61037f848285610336565b509392505050565b600082601f83011261039c5761039b61026f565b5b81356103ac848260208601610345565b91505092915050565b600080604083850312156103cc576103cb61022f565b5b60006103da8582860161025a565b925050602083013567ffffffffffffffff8111156103fb576103fa610234565b5b61040785828601610387565b9150509250929050565b60008115159050919050565b61042681610411565b82525050565b6000602082019050610441600083018461041d565b92915050565b600081519050919050565b600081905092915050565b60005b8381101561047b578082015181840152602081019050610460565b60008484015250505050565b600061049282610447565b61049c8185610452565b93506104ac81856020860161045d565b80840191505092915050565b60006104c48284610487565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061051657607f821691505b602082108103610529576105286104cf565b5b50919050565b60008190508160005260206000209050919050565b60008154610551816104fe565b61055b8186610452565b94506001821660008114610576576001811461058b576105be565b60ff19831686528115158202860193506105be565b6105948561052f565b60005b838110156105b657815481890152600182019150602081019050610597565b838801955050505b50505092915050565b60006105d38284610544565b915081905092915050565b600082825260208201905092915050565b7f446f63756d656e7420616c726561647920657869737473000000000000000000600082015250565b60006106256017836105de565b9150610630826105ef565b602082019050919050565b6000602082019050818103600083015261065481610618565b9050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026106a87fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261066b565b6106b2868361066b565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b60006106f96106f46106ef846106ca565b6106d4565b6106ca565b9050919050565b6000819050919050565b610713836106de565b61072761071f82610700565b848454610678565b825550505050565b600090565b61073c61072f565b61074781848461070a565b505050565b5b8181101561076b57610760600082610734565b60018101905061074d565b5050565b601f8211156107b0576107818161052f565b61078a8461065b565b81016020851015610799578190505b6107ad6107a58561065b565b83018261074c565b50505b505050565b600082821c905092915050565b60006107d3600019846008026107b5565b1980831691505092915050565b60006107ec83836107c2565b9150826002028217905092915050565b61080582610447565b67ffffffffffffffff81111561081e5761081d61028a565b5b61082882546104fe565b61083382828561076f565b600060209050601f8311600181146108665760008415610854578287015190505b61085e85826107e0565b8655506108c6565b601f1984166108748661052f565b60005b8281101561089c57848901518255600182019150602085019450602081019050610877565b868310156108b957848901516108b5601f8916826107c2565b8355505b6001600288020188555050505b505050505050565b60006108d982610447565b6108e381856105de565b93506108f381856020860161045d565b6108fc81610279565b840191505092915050565b6000602082019050818103600083015261092181846108ce565b90509291505056fea264697066735822122017fe21969d2dcfd8cf691335f333d4a03682795d0af143b13b8c1659a426fe2464736f6c63430008120033\n";

    public static final String FUNC_ADDDOCUMENT = "addDocument";

    public static final String FUNC_VERIFYDOCUMENT = "verifyDocument";

    public static final Event DOCUMENTADDED_EVENT = new Event("DocumentAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected BlockDoc(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected BlockDoc(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected BlockDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected BlockDoc(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<DocumentAddedEventResponse> getDocumentAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DOCUMENTADDED_EVENT, transactionReceipt);
        ArrayList<DocumentAddedEventResponse> responses = new ArrayList<DocumentAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DocumentAddedEventResponse typedResponse = new DocumentAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.metadata = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DocumentAddedEventResponse> documentAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, DocumentAddedEventResponse>() {
            @Override
            public DocumentAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DOCUMENTADDED_EVENT, log);
                DocumentAddedEventResponse typedResponse = new DocumentAddedEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.metadata = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DocumentAddedEventResponse> documentAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DOCUMENTADDED_EVENT));
        return documentAddedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addDocument(byte[] hash, String metadata) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDDOCUMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.Utf8String(metadata)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> verifyDocument(byte[] hash, String metadata) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VERIFYDOCUMENT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.Utf8String(metadata)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static BlockDoc load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new BlockDoc(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static BlockDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new BlockDoc(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static BlockDoc load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new BlockDoc(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static BlockDoc load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new BlockDoc(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<BlockDoc> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BlockDoc.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BlockDoc> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BlockDoc.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<BlockDoc> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(BlockDoc.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<BlockDoc> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(BlockDoc.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class DocumentAddedEventResponse extends BaseEventResponse {
        public byte[] hash;

        public String metadata;
    }
}
