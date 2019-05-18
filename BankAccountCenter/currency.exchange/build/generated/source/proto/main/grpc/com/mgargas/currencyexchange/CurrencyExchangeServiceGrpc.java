package com.mgargas.currencyexchange;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: currency_exchange.proto")
public final class CurrencyExchangeServiceGrpc {

  private CurrencyExchangeServiceGrpc() {}

  public static final String SERVICE_NAME = "CurrencyExchangeService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest,
      com.mgargas.currencyexchange.CurrencyRates> getGetForeignCurrenciesRatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getForeignCurrenciesRates",
      requestType = com.mgargas.currencyexchange.CurrencyRequest.class,
      responseType = com.mgargas.currencyexchange.CurrencyRates.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest,
      com.mgargas.currencyexchange.CurrencyRates> getGetForeignCurrenciesRatesMethod() {
    io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest, com.mgargas.currencyexchange.CurrencyRates> getGetForeignCurrenciesRatesMethod;
    if ((getGetForeignCurrenciesRatesMethod = CurrencyExchangeServiceGrpc.getGetForeignCurrenciesRatesMethod) == null) {
      synchronized (CurrencyExchangeServiceGrpc.class) {
        if ((getGetForeignCurrenciesRatesMethod = CurrencyExchangeServiceGrpc.getGetForeignCurrenciesRatesMethod) == null) {
          CurrencyExchangeServiceGrpc.getGetForeignCurrenciesRatesMethod = getGetForeignCurrenciesRatesMethod = 
              io.grpc.MethodDescriptor.<com.mgargas.currencyexchange.CurrencyRequest, com.mgargas.currencyexchange.CurrencyRates>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CurrencyExchangeService", "getForeignCurrenciesRates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.mgargas.currencyexchange.CurrencyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.mgargas.currencyexchange.CurrencyRates.getDefaultInstance()))
                  .setSchemaDescriptor(new CurrencyExchangeServiceMethodDescriptorSupplier("getForeignCurrenciesRates"))
                  .build();
          }
        }
     }
     return getGetForeignCurrenciesRatesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest,
      com.mgargas.currencyexchange.CurrencyRate> getGetForeignCurrenciesStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getForeignCurrenciesStream",
      requestType = com.mgargas.currencyexchange.CurrencyRequest.class,
      responseType = com.mgargas.currencyexchange.CurrencyRate.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest,
      com.mgargas.currencyexchange.CurrencyRate> getGetForeignCurrenciesStreamMethod() {
    io.grpc.MethodDescriptor<com.mgargas.currencyexchange.CurrencyRequest, com.mgargas.currencyexchange.CurrencyRate> getGetForeignCurrenciesStreamMethod;
    if ((getGetForeignCurrenciesStreamMethod = CurrencyExchangeServiceGrpc.getGetForeignCurrenciesStreamMethod) == null) {
      synchronized (CurrencyExchangeServiceGrpc.class) {
        if ((getGetForeignCurrenciesStreamMethod = CurrencyExchangeServiceGrpc.getGetForeignCurrenciesStreamMethod) == null) {
          CurrencyExchangeServiceGrpc.getGetForeignCurrenciesStreamMethod = getGetForeignCurrenciesStreamMethod = 
              io.grpc.MethodDescriptor.<com.mgargas.currencyexchange.CurrencyRequest, com.mgargas.currencyexchange.CurrencyRate>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "CurrencyExchangeService", "getForeignCurrenciesStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.mgargas.currencyexchange.CurrencyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.mgargas.currencyexchange.CurrencyRate.getDefaultInstance()))
                  .setSchemaDescriptor(new CurrencyExchangeServiceMethodDescriptorSupplier("getForeignCurrenciesStream"))
                  .build();
          }
        }
     }
     return getGetForeignCurrenciesStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CurrencyExchangeServiceStub newStub(io.grpc.Channel channel) {
    return new CurrencyExchangeServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CurrencyExchangeServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CurrencyExchangeServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CurrencyExchangeServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class CurrencyExchangeServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getForeignCurrenciesRates(com.mgargas.currencyexchange.CurrencyRequest request,
        io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRates> responseObserver) {
      asyncUnimplementedUnaryCall(getGetForeignCurrenciesRatesMethod(), responseObserver);
    }

    /**
     */
    public void getForeignCurrenciesStream(com.mgargas.currencyexchange.CurrencyRequest request,
        io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRate> responseObserver) {
      asyncUnimplementedUnaryCall(getGetForeignCurrenciesStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetForeignCurrenciesRatesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.mgargas.currencyexchange.CurrencyRequest,
                com.mgargas.currencyexchange.CurrencyRates>(
                  this, METHODID_GET_FOREIGN_CURRENCIES_RATES)))
          .addMethod(
            getGetForeignCurrenciesStreamMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                com.mgargas.currencyexchange.CurrencyRequest,
                com.mgargas.currencyexchange.CurrencyRate>(
                  this, METHODID_GET_FOREIGN_CURRENCIES_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class CurrencyExchangeServiceStub extends io.grpc.stub.AbstractStub<CurrencyExchangeServiceStub> {
    private CurrencyExchangeServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeServiceStub(channel, callOptions);
    }

    /**
     */
    public void getForeignCurrenciesRates(com.mgargas.currencyexchange.CurrencyRequest request,
        io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRates> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetForeignCurrenciesRatesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getForeignCurrenciesStream(com.mgargas.currencyexchange.CurrencyRequest request,
        io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRate> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetForeignCurrenciesStreamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CurrencyExchangeServiceBlockingStub extends io.grpc.stub.AbstractStub<CurrencyExchangeServiceBlockingStub> {
    private CurrencyExchangeServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.mgargas.currencyexchange.CurrencyRates getForeignCurrenciesRates(com.mgargas.currencyexchange.CurrencyRequest request) {
      return blockingUnaryCall(
          getChannel(), getGetForeignCurrenciesRatesMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.mgargas.currencyexchange.CurrencyRate> getForeignCurrenciesStream(
        com.mgargas.currencyexchange.CurrencyRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getGetForeignCurrenciesStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CurrencyExchangeServiceFutureStub extends io.grpc.stub.AbstractStub<CurrencyExchangeServiceFutureStub> {
    private CurrencyExchangeServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CurrencyExchangeServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CurrencyExchangeServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CurrencyExchangeServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.mgargas.currencyexchange.CurrencyRates> getForeignCurrenciesRates(
        com.mgargas.currencyexchange.CurrencyRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getGetForeignCurrenciesRatesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FOREIGN_CURRENCIES_RATES = 0;
  private static final int METHODID_GET_FOREIGN_CURRENCIES_STREAM = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CurrencyExchangeServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CurrencyExchangeServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FOREIGN_CURRENCIES_RATES:
          serviceImpl.getForeignCurrenciesRates((com.mgargas.currencyexchange.CurrencyRequest) request,
              (io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRates>) responseObserver);
          break;
        case METHODID_GET_FOREIGN_CURRENCIES_STREAM:
          serviceImpl.getForeignCurrenciesStream((com.mgargas.currencyexchange.CurrencyRequest) request,
              (io.grpc.stub.StreamObserver<com.mgargas.currencyexchange.CurrencyRate>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CurrencyExchangeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CurrencyExchangeServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.mgargas.currencyexchange.CurrencyExchange.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CurrencyExchangeService");
    }
  }

  private static final class CurrencyExchangeServiceFileDescriptorSupplier
      extends CurrencyExchangeServiceBaseDescriptorSupplier {
    CurrencyExchangeServiceFileDescriptorSupplier() {}
  }

  private static final class CurrencyExchangeServiceMethodDescriptorSupplier
      extends CurrencyExchangeServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CurrencyExchangeServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CurrencyExchangeServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CurrencyExchangeServiceFileDescriptorSupplier())
              .addMethod(getGetForeignCurrenciesRatesMethod())
              .addMethod(getGetForeignCurrenciesStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
