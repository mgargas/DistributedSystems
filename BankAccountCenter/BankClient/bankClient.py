#!/usr/bin/env python

import sys

from com.mgargas.bank import AccountCreator
from com.mgargas.bank import StandardManager
from com.mgargas.bank import PremiumManager


sys.path.append('../gen-py')


from com.mgargas.bank.ttypes import *

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TMultiplexedProtocol

try:

  # Make socket
  register_transport = TSocket.TSocket('localhost', 9090)

  # Buffering is critical. Raw sockets are very slow
  register_transport = TTransport.TBufferedTransport(register_transport)

  # Wrap in a protocol
  protocol = TBinaryProtocol.TBinaryProtocol(register_transport)

  # Create a client to use the protocol encoder
  #client = AccountCreator.Client(protocol)
  register_client = StandardManager.Client(protocol)
  # Connect!
  register_transport.open()

  manager_transport = TSocket.TSocket('localhost', 9091)
  manager_transport = TTransport.TFramedTransport(manager_transport)
  manager_transport.open()
  manager_protocol = TBinaryProtocol.TBinaryProtocol(manager_transport, True, True)

  manager_multiplex = TMultiplexedProtocol.TMultiplexedProtocol(TProtocol, "Standard")
  standard = StandardManager.Client(manager_multiplex)


  try:
    product = client.registerClient(UserData("Marek", "Gargas", UID(97042508975), Money(100000)), Money(1000))
    print(product)
  except InvalidGuid, ig:
    print(ig.reason)

  # Close!
  #transport.close()

except Thrift.TException, tx:
  print '%s' % (tx.message)