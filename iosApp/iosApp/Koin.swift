//
//  Koin.swift
//  iosApp
//
//  Created by Omara on 28/11/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}


func startKoin(){
    let koinApplication = KoinKt.doInitKoinIOS()
    _koin = koinApplication.koin
}
 
