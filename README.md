# KotlinVpExample
a kotlin VP sample except model part

## 项目介绍
[https://blog.csdn.net/villa_mou/article/details/81185903](https://blog.csdn.net/villa_mou/article/details/81185903)

* ps:模式和我之前写的mvp基本相同,只是去除了modle部分,不过多介绍

## 项目使用

在gradle文件里面添加

`implementation 'com.mou:vpLib:1.4.0'`

## 版本

* v1.3.0优化网络请求写法

用法1:

      MainRetrofit.apiService.getArticle()
                .bindDialogAndDisposable(mView, this)
                .onResult {
                    succuss.invoke(it)
                }

用法2:

     http<ListBean<ArticleData>> {
            api {
                MainRetrofit.apiService.getArticle()
            }
            loadingView(mView)
            disPool(this@LoginPresenter)
            onSuccess {
                showToastBottom("成功")
            }
            onError {

            }
        }

两种效果是一样的


* 网络baseurl 以及请求头封装(可以添加token,以及其他请求头)

    	object MainRetrofit : RetrofitFactory<MainApi>() {
    
    	override fun getBaseUrl()="https://www.wanandroid.com/"
    
    	override fun getHeader(builder: Request.Builder): Request.Builder {
        	return builder.addHeader("token","1233333333333333")
    	}
    	override fun getApiService(): Class<MainApi> {
        	return MainApi::class.java
    	}
		}
	

## Licenses
     Copyright 2019 villa_mou
    
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.